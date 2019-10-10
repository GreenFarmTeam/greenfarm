package com.nchu.ruanko.greenfarm.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import com.nchu.ruanko.greenfarm.service.ProductService;
import com.nchu.ruanko.greenfarm.service.ProductTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @ApiOperation(value = "/index", notes = "加载全部的农场品类型，店铺主界面")
    @GetMapping(value = "/index")
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductType> productTypeList = productTypeService.listAllProductTypes();
        modelAndView.setViewName("/shop/index");
        modelAndView.addObject("productTypeList",productTypeList);
        return modelAndView;
    }

    @ApiOperation(value = "loadAllProducts", notes = "加载全部的农场品，店铺主界面")
    @GetMapping(value = "/member/shop/load/products/operation")
    @ResponseBody
    public String  loadAllProduct(){
        return "";
    }





}