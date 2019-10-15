package com.nchu.ruanko.greenfarm.controller.management.user;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import com.nchu.ruanko.greenfarm.service.ProductTypeService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * “用户/会员申请成为商家”功能控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "management.user.UserApplyBusinessController", description = "“用户/会员申请成为商家”功能控制器")
@Controller
public class UserApplyBusinessController {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private BusinessService businessService;

    /**
     * 跳转至“会员/用户申请成为商家”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userApplyBusinessPage", notes = "跳转至“会员/用户申请成为商家”的页面")
    @GetMapping(value = "/user/management/business/apply")
    public ModelAndView userApplyBusinessPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductType> productTypeList = productTypeService.listAllProductTypes();
        modelAndView.setViewName("management/user/business-become");
        modelAndView.addObject("productTypeList", productTypeList);
        return modelAndView;
    }

    /**
     * 会员/用户申请成为商家
     *
     * @param shopName 店铺名称
     * @param shopDescription 店铺描述
     * @param shopScopes 店铺经营范围
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userApplyBusinessOperation", notes = "会员/用户申请成为商家")
    @PostMapping(value = "/user/management/business/apply/operation")
    @ResponseBody
    public String userApplyBusinessOperation(@RequestParam(name = "shopName") String shopName, @RequestParam(name = "shopDescription") String shopDescription, @RequestParam(name = "shopScopes") String[] shopScopes, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        Business business = new Business();
        String userUid = ((User) session.getAttribute("user")).getUserUid();
        if (businessService.checkUniqueBusinessByUserUID(userUid)) {
            // 说明用户是“首次申请成为商家”，之前没申请过
            // 如果之前申请过，数据会保留在
            String businessUid = StringUtils.createUUID();
            business.setBusinessUid(businessUid);
            business.setBusinessShopName(shopName);
            business.setBusinessShopDescription(shopDescription);
            businessService.addBusiness(business, userUid);
            // 添加“经营范围”
            for (String shopScope : shopScopes) {
                businessService.addBusinessScope(StringUtils.createUUID(), businessUid, shopScope.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("]", ""));
            }
            // 创建一条新的“审核记录”
            BusinessReview review = new BusinessReview();
            review.setReviewUid(StringUtils.createUUID());
            review.setReviewSubmitTime(new Date());
            businessService.addBusinessReviewRecord(review, businessUid);
            json.put("flag", true);
        } else {
            // 两种情况：1、用户当前的申请正在审核中；2、用户前面申请没通过，再次申请
            String businessUid = businessService.getBusinessUIDByUserUID(userUid);
             if (businessService.checkExistUnfinishedBusinessReviewByBusinessUID(businessUid)) {
                 // 用户当前的申请正在审核中
                 json.put("flag", false);
                 json.put("reason", "当前已有申请审核中，请勿重复提交！");
             } else {
                 // 用户前面申请没通过，再次申请
                 // 更新原来 gf_tb_business 中的数据
                 business.setBusinessShopName(shopName);
                 business.setBusinessShopDescription(shopDescription);
                 businessService.modifyBusinessReApplyByBusinessUID(business, businessUid);
                 // 添加“经营范围”
                 for (String shopScope : shopScopes) {
                     businessService.addBusinessScope(StringUtils.createUUID(), businessUid, shopScope.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("]", ""));
                 }
                 // 创建一条新的“审核记录”
                 BusinessReview review = new BusinessReview();
                 review.setReviewUid(StringUtils.createUUID());
                 review.setReviewSubmitTime(new Date());
                 businessService.addBusinessReviewRecord(review, businessUid);
                 json.put("flag", true);
             }
        }
        return json.toString();
    }

    /**
     * 跳转至“会员/用户申请成为商家的申请记录”的页面
     *
     * @param request HTTP 请求
     * @return ModelAndView
     */
    @ApiOperation(value = "userApplyBusinessHistoryPage", notes = "跳转至“会员/用户申请成为商家的申请记录”的页面")
    @GetMapping(value = "/user/management/business/apply/history")
    public ModelAndView userApplyBusinessHistoryPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        String userUid = ((User) session.getAttribute("user")).getUserUid();
        String businessUid = businessService.getBusinessUIDByUserUID(userUid);
        modelAndView.addObject("businessReviewList", businessService.listBusinessReviewsByBusinessUID(businessUid));
        modelAndView.setViewName("management/user/business-apply-record");
        return modelAndView;
    }

}