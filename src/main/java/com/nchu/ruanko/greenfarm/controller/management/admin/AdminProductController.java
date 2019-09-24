package com.nchu.ruanko.greenfarm.controller.management.admin;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "management.admin.AdminProductController", description = "“管理员商品管理”功能控制器")
@Controller
public class AdminProductController {

    @Autowired
    private ProductService productService;

    private static final int PAGE_NAVIGATION_SIZE = 10;

    /**
     * 跳转至“管理员商品审核”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "adminReviewProductPage", notes = "跳转至“管理员商品审核”的页面")
    @GetMapping(value = "/greenfarm/admin/management/product/review")
    public ModelAndView adminReviewProductPage(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/review-product");
        modelAndView.addObject("vo", productService.listAdminProductReview(pageNum, pageSize, PAGE_NAVIGATION_SIZE));
        return modelAndView;
    }

    /**
     * 管理员同意商品上架申请
     *
     * @param productReviewUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminReviewProductAgreeOperation", notes = "管理员同意商品上架申请")
    @GetMapping(value = "/greenfarm/admin/management/product/review/agree/{uid}/operation")
    @ResponseBody
    public String adminReviewProductAgreeOperation(@PathVariable(name = "uid") String productReviewUid) {
        JSONObject json = new JSONObject();
        productService.adminAgreeProductReview(productReviewUid);
        json.put("flag", true);
        return json.toString();
    }


    /**
     * 管理员驳回商品上架申请
     *
     * @param productReviewUid uid
     * @return JSON
     */
    @ApiOperation(value = "adminReviewProductDisagreeOperation", notes = "管理员驳回商品上架申请")
    @PostMapping(value = "/greenfarm/admin/management/product/review/disagree/{uid}/operation")
    @ResponseBody
    public String adminReviewProductDisagreeOperation(@RequestParam(name = "reason") String reason ,@PathVariable(name = "uid") String productReviewUid) {
        JSONObject json = new JSONObject();
        productService.adminDisagreeProductReview(reason, productReviewUid);
        json.put("flag", true);
        return json.toString();
    }

}