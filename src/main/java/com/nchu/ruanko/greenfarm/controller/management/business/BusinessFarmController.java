package com.nchu.ruanko.greenfarm.controller.management.business;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.constant.FarmUnitEnum;
import com.nchu.ruanko.greenfarm.constant.FileConstant;
import com.nchu.ruanko.greenfarm.constant.PageConstant;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import com.nchu.ruanko.greenfarm.service.FarmService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "management.business.BusinessFarmController", description = "“商家农场管理”功能控制器")
@Controller
public class BusinessFarmController {

    @Autowired
    private FarmService farmService;

    @ApiOperation(value = "businessManagementCanFarm", notes = "判断该商家是否能进行农场方面的业务")
    @GetMapping(value = "/business/management/farm/can")
    @ResponseBody
    public String businessManagementCanFarm(@RequestParam(name = "uid") String businessUid) {
        JSONObject json = new JSONObject();
        if (farmService.businessCanFarm(businessUid)) {
            json.put("flag", true);
        } else {
            json.put("flag", false);
        }
        return json.toString();
    }

    @ApiOperation(value = "businessManagementFarmAddPage", notes = "跳转至“发布农场租赁信息”页面")
    @GetMapping(value = "/business/management/farm/add")
    public ModelAndView businessManagementFarmAddPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/business/farm-add");
        modelAndView.addObject("farmTypeList", farmService.listFarmTypes());
        return modelAndView;
    }

    @ApiOperation(value = "businessManagementFarmAddOperation", notes = "发布农场租赁信息")
    @PostMapping(value = "/business/management/farm/add/operation")
    @ResponseBody
    public String businessManagementFarmAddOperation(@RequestParam(name = "type") String farmType, @RequestParam(name = "price") String farmPrice, @RequestParam(name = "year") String farmYear, @RequestParam(name = "area") String farmArea, @RequestParam(name = "unit") String farmUnit, @RequestParam(name = "description", required = false) String farmDescription, @RequestParam(name = "mainImage", required = false) MultipartFile mainImage, @RequestParam(name = "otherImages", required = false) MultipartFile[] otherImages, @RequestParam(name = "lng") String lng, @RequestParam(name = "lat") String lat, HttpServletRequest request) {
        JSONObject json = new JSONObject();

        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        String businessUid = business.getBusinessUid();

        Farm farm = new Farm();
        farm.setFarmPrice(Float.parseFloat(farmPrice));
        farm.setFarmYear(Integer.parseInt(farmYear));
        farm.setFarmArea(Integer.parseInt(farmArea));
        farm.setFarmLongitude(lng);
        farm.setFarmLatitude(lat);
        farm.setFarmDescription(farmDescription);
        farm.setFarmUnit(FarmUnitEnum.getValue(Integer.parseInt(farmUnit)));

        File folder = new File(FileConstant.FILE_UPLOAD_PATH);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }

        FarmImage farmMainImage = null;
        if (mainImage != null && !mainImage.isEmpty()) {
            String uid = StringUtils.createUUID();
            String fileNewName = uid + mainImage.getOriginalFilename().substring(mainImage.getOriginalFilename().lastIndexOf("."));
            try {
                mainImage.transferTo(new File(folder, fileNewName));
                farmMainImage = new FarmImage();
                farmMainImage.setFarmImageUid(uid);
                farmMainImage.setFarmImagePath(FileConstant.FILE_UPLOAD_VIRTUAL_PATH_PREFIX + fileNewName);
            } catch (IOException e) {
                json.put("flag", false);
                json.put("reason", "文件上传失败！system");
                e.printStackTrace();
                return json.toString();
            }
        }

        List<FarmImage> farmOtherImages = new ArrayList<>();
        if (otherImages != null && otherImages.length != 0) {
            for (MultipartFile otherImage : otherImages) {
                String uid = StringUtils.createUUID();
                String fileNewName = uid + otherImage.getOriginalFilename().substring(otherImage.getOriginalFilename().lastIndexOf("."));
                try {
                    otherImage.transferTo(new File(folder, fileNewName));
                    FarmImage farmOtherImage = new FarmImage();
                    farmOtherImage.setFarmImageUid(uid);
                    farmOtherImage.setFarmImagePath(FileConstant.FILE_UPLOAD_VIRTUAL_PATH_PREFIX + fileNewName);
                    farmOtherImages.add(farmOtherImage);
                } catch (IOException e) {
                    json.put("flag", false);
                    json.put("reason", "文件上传失败！system");
                    e.printStackTrace();
                    return json.toString();
                }
            }
        }

        farmService.businessAddFarm(farm, farmMainImage, farmOtherImages, farmType, businessUid);
        json.put("flag", true);

        return json.toString();
    }

    @ApiOperation(value = "businessManagementFarmReviewPage", notes = "跳转至“商家的农场信息审核记录”的页面")
    @GetMapping(value = "/business/management/farm/review")
    public ModelAndView businessManagementFarmReviewPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        modelAndView.setViewName("management/business/farm-review");
        modelAndView.addObject("vo", farmService.businessListFarmReviewRecords(business.getBusinessUid(), pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

    /**
     * 加载商家发布的农场的信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="businessManagementFarmUpPage",notes ="跳转至商家的上架农场页面")
    @GetMapping(value="/business/management/farm/up")
    public ModelAndView businessManagementFarmUpPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        modelAndView.setViewName("management/business/farm-upping");
        modelAndView.addObject("vo", farmService.businessListFarm(business.getBusinessUid(),pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE,11));
        return modelAndView;
    }

    /**
     * 加载商家下架的农场的信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="businessManagementFarmDownPage",notes ="跳转至商家的下架农场页面")
    @GetMapping(value="/business/management/farm/down")
    public ModelAndView businessManagementFarmDownPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Business business = (Business) session.getAttribute("business");
        modelAndView.setViewName("management/business/farm-down");
        modelAndView.addObject("vo", farmService.businessListFarm(business.getBusinessUid(),pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE,1));
        return modelAndView;
    }
    /**
     * 商家下架某农场的操作
     *
     * @param farmUid
     * @return
     */
    @ApiOperation(value = "businessFarmDownOperation", notes = "商家下架某农场的操作")
    @GetMapping(value = "/greenfarm/business/management/farm/down/operation")
    @ResponseBody
    public String businessFarmDownOperation(@RequestParam(name = "uid") String farmUid) {
        JSONObject json = new JSONObject();
        farmService.businessDownFarm(farmUid);
        json.put("flag", true);
        return json.toString();
    }

    /**
     * 商家上架某农场的操作
     *
     * @param farmUid
     * @return
     */
    @ApiOperation(value = "businessFarmDownOperation", notes = "商家上架某农场的操作")
    @GetMapping(value = "/greenfarm/business/management/farm/up/operation")
    @ResponseBody
    public String businessFarmUpOperation(@RequestParam(name = "uid") String farmUid) {
        JSONObject json = new JSONObject();
        farmService.businessUpFarm(farmUid);
        json.put("flag", true);
        return json.toString();
    }

}




