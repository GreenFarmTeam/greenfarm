package com.nchu.ruanko.greenfarm.controller.management.admin;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.constant.PageConstant;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminFarmVo;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminProductVO;
import com.nchu.ruanko.greenfarm.service.FarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "management.admin.AdminFarmController", description = "")
@Controller
public class AdminFarmController {
    @Autowired
    FarmService farmService;

    /**
     * 加载管理员审核合格的农场信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="loadAllLegalFarm", notes="加载所有的已经审核完毕的农场")
    @GetMapping(value = "/greenfarm/admin/management/farm")
    public ModelAndView loadAllLegalFarmPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/farm-checked");
        modelAndView.addObject("vo", farmService.adminListFarm(pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE,2));
        return modelAndView;
    }

    /**
     * 加载所有待审核的农场信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="loadAllLegalFarm", notes="加载所有未审核的农场")
    @GetMapping(value = "/greenfarm/admin/management/farm/review")
    public ModelAndView loadAllNoCheckedFarmPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/review-farm");
        modelAndView.addObject("vo", farmService.adminListFarm(pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE,0));
        return modelAndView;
    }
    /**
     * 加载所有管理员下架的农场信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="loadAllLegalFarm", notes="加载所有管理员下架的农场信息")
    @GetMapping(value = "/greenfarm/admin/management/farm/down")
    public ModelAndView loadAllAdminDownFarmPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/farm-downing");
        modelAndView.addObject("vo", farmService.adminListFarm(pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE,10));
        return modelAndView;
    }
    /**
     * 加载所有正在上架的农场信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="loadAllLegalFarm", notes="加载所有正在上架的农场信息")
    @GetMapping(value = "/greenfarm/admin/management/farm/up")
    public ModelAndView loadAllIsUppingFarmPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/upping-farm");
        modelAndView.addObject("vo", farmService.adminListFarm(pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE,11));
        return modelAndView;
    }

    /**
     * 管理员查看一个农场的详细信息
     * @param farmUid
     * @return
     */
    @ApiOperation(value = "adminFarmDetailOperation", notes = "管理员查看某一个农场的详细信息")
    @GetMapping(value = "/greenfarm/admin/management/farm/detail/operation")
    @ResponseBody
    public String adminFarmDetailOperation(@RequestParam(name = "uid") String farmUid) {
        JSONObject json = new JSONObject();
        AdminFarmVo vo = farmService.adminGetFarmByFarmUID(farmUid);
        json.put("uid", vo.getFarm().getFarmUid());
        json.put("name", vo.getFarm().getFarmName());
        json.put("price",vo.getFarm().getFarmPrice());
        json.put("farm_unit", vo.getFarm().getFarmUnit());
        json.put("description", vo.getFarm().getFarmDescription());
        json.put("farm_type_name", vo.getFarm().getType().getFarmTypeName());
        json.put("farm_area", vo.getFarm().getFarmArea());
        json.put("farm_lng", vo.getFarm().getFarmLongitude());
        json.put("farm_lat", vo.getFarm().getFarmLatitude());
        if (vo.getFarm().getFarmDescription() == null) {
            json.put("description", "暂无描述");
        } else {
            json.put("description", vo.getFarm().getFarmDescription());
        }
        switch (vo.getFarm().getFarmState()){
            case 0:json.put("farm_state", "未审核状态");break;
            case 11:json.put("farm_state", "上架状态");break;
            case 01:json.put("farm_state", "农场主下架状态");break;
            case 10:json.put("farm_state", "管理员下架状态");break;
            default:;break;

        }

        if (vo.getMainImage() == null) {
            json.put("mainImage", "农场暂无主图片");
        } else {
            json.put("mainImage", vo.getMainImage().getFarmImagePath());
        }
        List<String> pathList = new ArrayList<>();
        if (vo.getOtherImages().size() == 0) {
            json.put("otherImages", pathList);
        } else {
            for (FarmImage image : vo.getOtherImages()) {
                pathList.add(image.getFarmImagePath());
            }
            json.put("otherImages", pathList);
        }
        json.put("businessUid", vo.getFarm().getBusiness().getBusinessUid());
        json.put("businessName", vo.getFarm().getBusiness().getBusinessShopName());
        json.put("businessDescription", vo.getFarm().getBusiness().getBusinessShopDescription());
        json.put("username", vo.getFarm().getBusiness().getUser().getUserUsername());
        json.put("nickname", vo.getFarm().getBusiness().getUser().getUserNickname());

        System.out.println("进来了！");
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 管理员同意农场上架申请
     *
     * @param farmReviewUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminReviewFarmAgreeOperation", notes = "管理员同意农场的上架申请")
    @GetMapping(value = "/greenfarm/admin/management/farm/review/agree/{uid}/operation")
    @ResponseBody
    public String adminReviewFarmAgreeOperation(@PathVariable(name = "uid") String farmReviewUid) {
        JSONObject json = new JSONObject();
        farmService.adminAgreeFarmReview(farmReviewUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 管理员下架某农场的操作
     *
     * @param farmUid
     * @return
     */
    @ApiOperation(value = "adminFarmDownOperation", notes = "管理员下架某农场的操作")
    @GetMapping(value = "/greenfarm/admin/management/farm/down/operation")
    @ResponseBody
    public String adminFarmDownOperation(@RequestParam(name = "uid") String farmUid) {
        JSONObject json = new JSONObject();
        farmService.adminDownFarm(farmUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 管理员恢复上架农场操作
     *
     * @param farmUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminFarmUpOperation", notes = "管理员恢复上架农场操作")
    @GetMapping(value = "/greenfarm/admin/management/farm/up/operation")
    @ResponseBody
    public String adminFarmUpOperation(@RequestParam(name = "uid") String farmUid) {
        JSONObject json = new JSONObject();
        farmService.adminUpFarm(farmUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 管理员驳回农场上架的申请
     *
     * @param farmReviewUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminReviewFarmDisagreeOperation", notes = "管理员驳回农场上架申请")
    @PostMapping(value = "/greenfarm/admin/management/farm/review/disagree/{uid}/operation")
    @ResponseBody
    public String adminReviewFarmDisagreeOperation(@RequestParam(name = "reason") String reason ,@PathVariable(name = "uid") String farmReviewUid) {
        JSONObject json = new JSONObject();
        farmService.adminDisagreeFarmReview(reason, farmReviewUid);
        json.put("flag", true);
        return json.toString();
    }




}
