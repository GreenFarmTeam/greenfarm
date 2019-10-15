package com.nchu.ruanko.greenfarm.controller.management.user;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.constant.PageConstant;
import com.nchu.ruanko.greenfarm.pojo.entity.Address;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.AddressService;
import com.nchu.ruanko.greenfarm.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;

@Api(tags = "management.user.UserProductController", description = "“会员/产品中心”的控制器")
@Controller
public class UserProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AddressService addressService;
    private static final int PAGE_NAVIGATION_SIZE = 10;
    /**
     * 会员加载热门的产品
     *
     * @param
     * @return
     */
    @ApiOperation(value = "memberManagementLoadHotProductsOperation", notes = "会员加载热门的产品")
    @GetMapping(value = "/member/management/product/load/all/operation")
    @ResponseBody
    public String memberManagementLoadHotProductsOperation() {

        JSONObject json = new JSONObject();
        if(productService.loadAllHotProducts()!=null){
            json.put("productList",productService.loadAllHotProducts());
            json.put("flag", 1);
        }else
            json.put("flag", 0);

        return json.toString();
    }
    /**
     * 会员加载不热门的产品
     *
     * @param
     * @return
     */
    @ApiOperation(value = "memberManagementLoadHotProductsOperation", notes = "会员加载不热门的产品")
    @GetMapping(value = "/member/management/new/product/load/all/operation")
    @ResponseBody
    public String memberManagementLoadNotHotProductsOperation(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "5") int pageSize) {

        JSONObject json = new JSONObject();
        if(productService.loadAllHotProducts()!=null){
            json.put("productList",productService.loadAllNotHotProductsPage(pageNum,pageSize,PAGE_NAVIGATION_SIZE));
            json.put("flag", 1);
        }else
            json.put("flag", 0);

        return json.toString();
    }


    /**
     * 会员加载某个的产品的详细信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "memberManagementLoadProductsDetailInfo", notes = "会员加载产品的详细信息")
    @GetMapping(value = "/member/management/product/load/detail/operation")
    public ModelAndView memberManagementLoadProductsDetailInfo(@RequestParam("productID") String productId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("productList",productService.loadProductByproductID(productId));
        modelAndView.setViewName("");
        return modelAndView;
    }

    @ApiOperation(value = "memberPurchaseProducts", notes = "会员立即购买某个产品")
    @GetMapping(value="/member/management/product/purchase/{productId}")
    @ResponseBody
    public String  memberPurchaseProducts(@PathVariable("productId") String productId,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Product product = productService.memberGetProductByProductId(productId);
        List<Address> addressList = addressService.listAddressesByUserUID(user.getUserUid());
        jsonObject.put("product",product);
        jsonObject.put("addressList",addressList);
        return jsonObject.toString();

    }


}
