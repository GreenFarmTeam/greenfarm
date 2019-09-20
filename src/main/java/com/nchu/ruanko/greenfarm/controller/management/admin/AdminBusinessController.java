package com.nchu.ruanko.greenfarm.controller.management.admin;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminBusinessDetailVO;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import com.nchu.ruanko.greenfarm.service.UserService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "management.admin.AdminBusinessController", description = "")
@Controller
public class AdminBusinessController {


    @Autowired
    private BusinessService businessService;

    @Autowired
    private UserService userService;

    private static final int PAGE_NAVIGATION_SIZE = 10;

    /**
     * 商家申请记录
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "adminReviewBusinessPage", notes = "")
    @GetMapping(value = "/greenfarm/admin/management/business/review")
    public ModelAndView adminReviewBusinessPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/review-business");
        modelAndView.addObject("vo", businessService.listBusinessReviewsWithPage(pageNum, pageSize, PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

    /**
     * 查看商家申请详情
     * @param reviewUid
     * @return
     */
    @ApiOperation(value = "adminReviewBusinessDetailPage", notes = "")
    @GetMapping(value = "/greenfarm/admin/management/business/review/detail/{reviewUid}")
    public ModelAndView adminReviewBusinessDetailPage(@PathVariable(name = "reviewUid") String reviewUid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/review-business-detail");
        modelAndView.addObject("vo", businessService.getBusinessReviewDetailByReviewUID(reviewUid));
        return modelAndView;
    }

    /**
     * 管理员同意申请
     * @param reviewUid
     * @param request
     * @return
     */
    @ApiOperation(value = "adminAgreeReview", notes = "")
    @GetMapping(value = "/greenfarm/admin/management/business/review/agree/{reviewUid}")
    @ResponseBody
    public String adminAgreeReview(@PathVariable(name = "reviewUid") String reviewUid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String userUid = businessService.agreeApply(reviewUid);
        HttpSession session = request.getSession();
        session.setAttribute("user", userService.getUserByUID(userUid));
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 跳转到驳回理由界面
     * @param reviewUid
     * @return
     */
    @ApiOperation(value = "disagreeReviewPage", notes = "")
    @GetMapping("/greenfarm/admin/management/business/review/disagree/{reviewUid}")
    public ModelAndView disagreeReviewPage(@PathVariable(name = "reviewUid") String reviewUid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/review-business-disagree-reason");
        modelAndView.addObject("reviewUid", reviewUid);
        return modelAndView;
    }

    /**
     * 商家申请驳回申请
     * @param reviewUid
     * @param reason
     * @return
     */
    @ApiOperation(value = "disagreeReviewOperation", notes = "")
    @PostMapping(value = "/greenfarm/admin/management/business/review/disagree/{reviewUid}/operation")
    @ResponseBody
    public String disagreeReviewOperation(@PathVariable(name = "reviewUid") String reviewUid, @RequestParam(name = "reason") String reason) {
        JSONObject json = new JSONObject();
        businessService.disagree(reviewUid, reason);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 加载所有的已审核的合法商家
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="loadAllLegalBusiness", notes="加载所有的已审核商家")
    @GetMapping(value="/greenfarm/admin/management/business/loadAll")
    public ModelAndView loadAllLegalBusiness(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("vo", businessService.listAllLegalBusinessWithPage(pageNum, pageSize, PAGE_NAVIGATION_SIZE));
        modelAndView.setViewName("management/admin/business-checked");
        /*businessService.listAllLegalBusinessWithPage(pageNum, pageSize, PAGE_NAVIGATION_SIZE);*/
        return modelAndView;
    }

    /**
     * 查看商家详情
     * @param businessUid
     * @return
     */
    @ApiOperation(value = "adminBusinessDetailPage", notes = "查看商家的详情")
    @GetMapping(value = "/greenfarm/admin/management/business/detail/{businessUid}")
    public ModelAndView adminBusinessDetailPage(@PathVariable(name = "businessUid") String businessUid) {
        ModelAndView modelAndView = new ModelAndView();
        AdminBusinessDetailVO vo = new AdminBusinessDetailVO();
        modelAndView.setViewName("management/admin/business-detail");
        vo.setBusiness(businessService.getBusinessDetailByBusinessUID(businessUid));
        vo.setBusinessScopeList(businessService.listBusinessScopesByBusinessUID(businessUid));
        modelAndView.addObject("vo",vo);
        return modelAndView;
    }

}