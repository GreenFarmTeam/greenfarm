package com.nchu.ruanko.greenfarm.controller.management.user;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.pojo.entity.Address;
import com.nchu.ruanko.greenfarm.pojo.entity.OrderItem;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminProductVO;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderItemVo;
import com.nchu.ruanko.greenfarm.service.AddressService;
import com.nchu.ruanko.greenfarm.service.OrderItemService;
import com.nchu.ruanko.greenfarm.service.OrderService;
import com.nchu.ruanko.greenfarm.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = "management.user.UserCartController", description = "“会员/购物车中心”的控制器")
@Controller
public class UserCartController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AddressService addressService;

    /**
     * 跳转到购物车界面
     * @return
     */
    @ApiOperation(value = "memberManagementLoadProductsDetailInfo", notes = "跳转到购物车界面")
    @GetMapping("/member/management/toCart.html")
    public String toCart(){

        return "shop/cart";
    }

    @ApiOperation(value = "addProductToCart", notes = "增加商品到购物车中")
    @GetMapping(value = "/user/management/cart/add/product/{productId}")
    @ResponseBody
    public String addProductToCart(@PathVariable("productId") String productId, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");




        if(orderService.addProductToCart(productId,user.getUserUid())){
            jsonObject.put("flag",1);
        }else{
            jsonObject.put("flag",0);
        }
        return jsonObject.toString();
    }
    @ApiOperation(value = "loadUserCart", notes = "加载用户购物车中的产品以及用户的收货地址")
    @GetMapping(value = "/user/management/cart/load")
    @ResponseBody
    public String loadCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        List<OrderItemVo> orderItemList = orderItemService.loadCartOrderItem(user.getUserUid());
        List<Address> addressList = addressService.listAddressesByUserUID(user.getUserUid());
        jsonObject.put("data",orderItemList);
        jsonObject.put("addressData",addressList);
        jsonObject.put("flag",1);
        return jsonObject.toString();
    }

    @ApiOperation(value = "deleteProductFromUserCart", notes = "用户删除购物车中某个产品")
    @GetMapping(value = "/user/management/cart/delete/product/{productId}")
    @ResponseBody
    public String deleteProductFromUserCart(@PathVariable("productId") String productId, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        JSONObject jsonObject = new JSONObject();

        if(orderItemService.deleteProductFromUserCart(user.getUserUid(),productId)){
            jsonObject.put("flag",1);
        }else{
            jsonObject.put("flag",0);
        }

        return jsonObject.toString();
    }

    @ApiOperation(value = "alterProductNumFromUserCart", notes = "用户改变购物车中某个产品的数量")
    @GetMapping(value = "/user/management/cart/alter/productNum/{productId}/{productNum}")
    @ResponseBody
    public String alterProductNumFromUserCart(@PathVariable("productId") String productId,@PathVariable("productNum") String productNum, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        AdminProductVO adminProductVO = productService.adminGetProductByProductUID(productId);
        int productTNum  =  Integer.parseInt(productNum);
        if(orderItemService.alterProductNumFromUserCart(user.getUserUid(),productId,productTNum)){
            jsonObject.put("productNum",productNum);
            jsonObject.put("itemSumPrice",productTNum*adminProductVO.getProduct().getProductPrice());
            List<OrderItemVo> orderItemList = orderItemService.loadCartOrderItem(user.getUserUid());
            jsonObject.put("data",orderItemList);
            jsonObject.put("flag",1);
        }else{
            jsonObject.put("flag",0);
        }

        return jsonObject.toString();
    }




}
