package com.nchu.ruanko.greenfarm.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductType;
import com.nchu.ruanko.greenfarm.pojo.vo.MemberProductPageVo;
import com.nchu.ruanko.greenfarm.pojo.vo.MemberProductVo;
import com.nchu.ruanko.greenfarm.service.ProductService;
import com.nchu.ruanko.greenfarm.service.ProductTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class ShopController {

    @Autowired
    private ProductService productService;



    @Autowired
    private ProductTypeService productTypeService;

    private static final int PAGE_NAVIGATION_SIZE = 10;

    /**
     * 访问首页
     *
     * @return
     */
    @ApiOperation(value = "index", notes = "访问网站商品主界面")
    @RequestMapping("/index")
    public String toIndex() {
        return "shop/index";
    }
    /**
     * 加载所有的农场品类型
     * @return
     */
    @ApiOperation(value = "/greenfarm/shop/load/list/classification", notes = "加载全部的农场品类型，店铺主界面")
    @GetMapping(value = "/greenfarm/shop/load/list/classification")
    @ResponseBody
    public String loadAllShopClassification() {
        JSONObject json = new JSONObject();
        List<ProductType> productTypeList = productTypeService.listAllProductTypes();

        json.put("flag",0);
        json.put("data",productTypeList);
        return json.toString();
    }

    /**
     * 根据产品类型ID加载产品类型
     * @param classificationId
     * @return
     */
    @ApiOperation(value = "/greenfarm/shop/load/classification/{productTypeId}", notes = "根据产品类型ID加载产品类型全部信息")
    @GetMapping(value = "/greenfarm/shop/load/classification/{classificationId}")
    public ModelAndView loadClassificationByClassificationID(@PathVariable(name="classificationId") String classificationId) {

        ModelAndView ModelAndView = new ModelAndView();
        ModelAndView.setViewName("shop/category-index");
        ModelAndView.addObject("category",productTypeService.loadProuctTypeById(classificationId));
        /*json.put("state",0);*/

        return ModelAndView;
    }

    /**
     * 加载所有的农场品类型
     * @return
     */
    @ApiOperation(value = "loadProductsByClassificationID", notes = "根据商品类型加载产品")
    @GetMapping(value = "/greenfarm/shop/load/products/{classificationId}")
    @ResponseBody
    public String loadProductsByClassificationID(@PathVariable(name="classificationId") String classificationId, @RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "5") int pageSize) {
        JSONObject json = new JSONObject();
        MemberProductPageVo productList = productService.listAllProductByclassificationId(classificationId,pageNum,pageSize,PAGE_NAVIGATION_SIZE);
        json.put("flag",0);
        json.put("productList",productList);
        return json.toString();
    }



    /**
     * 加载全部的农场品
     * @return
     */
    @ApiOperation(value = "loadAllProducts", notes = "加载全部的农场品，店铺主界面")
    @GetMapping(value = "/member/shop/load/products/operation")
    @ResponseBody
    public String  loadAllProduct(){
        return "";
    }

    /**
     * 会员加载某个的产品的详细信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "memberManagementLoadProductsDetailInfo", notes = "加载产品的详细信息")
    @GetMapping(value = "/member/shop/product/load/detail/operation")
    public ModelAndView memberManagementLoadProductsDetailInfo(@RequestParam("id") String productID) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product",productService.loadProductByproductID(productID));
        modelAndView.setViewName("shop/product-detail");
        return modelAndView;
    }







}