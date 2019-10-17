package com.nchu.ruanko.greenfarm.controller.management.user;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.config.AlipayConfig;
import com.nchu.ruanko.greenfarm.pojo.entity.*;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderItemVo;
import com.nchu.ruanko.greenfarm.service.OrderFarmService;
import com.nchu.ruanko.greenfarm.service.OrderItemService;
import com.nchu.ruanko.greenfarm.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api(tags = "management.user.UserProductController", description = "“会员/订单中心”的控制器")
@Controller
public class UserOrderController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderFarmService orderFarmService;


    @ApiOperation(value = "member/order", notes = "跳转至我的订单界面")
    @GetMapping(value="/member/management/order/index")
    public ModelAndView toOrderIndexHtml(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/order-list");
        return modelAndView;
    }
    @ApiOperation(value = "userSubmitOrder", notes = "会员提交订单功能")
    @PostMapping(value="/member/management/order/add/operation")
    @ResponseBody
    public String userSubmitOrder(@RequestParam("name")String name, @RequestParam("phone")String phone, @RequestParam("addr")String addr, @RequestParam("detailAddr")String detailAddr,@RequestParam("totalPrice")String totalPrice, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        HttpSession  session = request.getSession();
        User user = (User) session.getAttribute("user");
        String result = orderService.userSubmitOrder(user.getUserUid(),name,phone,addr,detailAddr,totalPrice);
        if(result==null){
            jsonObject.put("flag",1);

        }else{
            jsonObject.put("flag",0);
            jsonObject.put("result",result);
        }
        return jsonObject.toString();
    }

    @ApiOperation(value = "userLoadAllFarmOrders", notes = "会员加载所有的农场的订单")
    @GetMapping(value="/member/management/order/farm/load/all/operation")
    @ResponseBody
    public String userLoadAllFarmOrders(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();

        HttpSession session = request.getSession();
        User user= (User)session.getAttribute("user");
        List<OrderFarm>  orderFarmList = orderFarmService.listAllOrderFarmsByUserId(user.getUserUid());
        jsonObject.put("data",orderFarmList);
        jsonObject.put("flag",1);
        return jsonObject.toString();
    }

    @ApiOperation(value = "userLoadAllOrders", notes = "会员加载所有的订单")
    @GetMapping(value="/member/management/order/load/all/operation")
    @ResponseBody
    public String userLoadAllOrders(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();

        HttpSession session = request.getSession();
        User user= (User)session.getAttribute("user");
        List<Order> orderList = orderService.loadAllOrdersBy(user.getUserUid());
        jsonObject.put("data",orderList);
        jsonObject.put("flag",1);
        return jsonObject.toString();

    }
    @ApiOperation(value = "userLoadAllOrders", notes = "会员根据订单加载所有的订单子项")
    @GetMapping(value="/member/management/orderItem/load/{orderId}")
    @ResponseBody
    public String userLoadAllOrdersItem(@PathVariable("orderId")String orderId){
        JSONObject jsonObject = new JSONObject();

        List<OrderItemVo> orderItemList = orderItemService.loadOrderItemsByOrderId(orderId);

        jsonObject.put("data",orderItemList);
        jsonObject.put("flag",1);

        return jsonObject.toString();

    }

    @ApiOperation(value = "userPayOrder", notes = "会员产品支付产品订单")
    @GetMapping(value = "/member/management/order/pay/{orderId}")
    @ResponseBody
    public String userPayOrder(@PathVariable(name = "orderId") String orderId, HttpServletRequest request) throws IOException {
        System.out.println("orderId:"+orderId);
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        AlipayTradePagePayResponse payResponse = orderService.payOrderByUserIdAndOrderId(user.getUserUid(),orderId);

        if(payResponse!=null){
            jsonObject.put("responseHtml",payResponse.getBody());
            jsonObject.put("flag",1);
        }
        return jsonObject.toString();
    }


    @ApiOperation(value = "userPayFarmOrder", notes = "会员支付农场订单")
    @GetMapping(value="/member/management/farm/order/pay/{orderFarmId}")
    @ResponseBody
    public  String userPayFarmOrder(@PathVariable(name = "orderFarmId") String orderFarmId, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        AlipayTradePagePayResponse payResponse = orderService.payFarmOrderByUserIdAndOrderId(user.getUserUid(),orderFarmId);
        if(payResponse!=null){
            jsonObject.put("responseHtml",payResponse.getBody());
            jsonObject.put("flag",1);
        }
        return jsonObject.toString();

    }



    @ApiOperation(value = "userPayOrder", notes = "会员支付界面跳转")
    @GetMapping(value="member/management/toPay.html/{htmlContent}")
    public ModelAndView toPayHtml(@PathVariable("htmlContent") String htmlContent){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/pay");
        modelAndView.addObject("payHTML",htmlContent);
        return modelAndView;
    }

    @ApiOperation(value = "userConfirmReceiveProducts", notes = "会员确认收货")
    @GetMapping(value="/member/management/order/confirm/receive/{orderId}")
    @ResponseBody
    public String userConfirmReceiveProducts(@PathVariable("orderId")String orderId,HttpServletRequest request){
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(orderService.confirmReceiveProducts(orderId,user.getUserUid())){
            json.put("flag",true);
        }else{
            json.put("flag",false);
        }
        return json.toString();
    }

    @ApiOperation(value = "userConfirmOrderFarms", notes = "会员确认已旅游农场")
    @GetMapping(value="/member/management/farm/order/confirm/{orderFarmId}")
    @ResponseBody
    public String userConfirmOrderFarms(@PathVariable("orderFarmId") String orderFarmId){
        JSONObject jsonObject = new JSONObject();
        if(orderService.confirmOrderFarms(orderFarmId)){
            jsonObject.put("flag",true);
        }else{
            jsonObject.put("flag",false);
        }
        return jsonObject.toString();
    }

    @ApiOperation(value = "userConfirmReceiveProducts", notes = "商家安排发货")
    @GetMapping(value="/business/management/order/arrange/send/{orderId}")
    @ResponseBody
    public String businessArrangeSendProducts(@PathVariable("orderId")String orderId,HttpServletRequest request){
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(orderService.arrangeSendProducts(orderId,user.getUserUid())){
            json.put("flag",true);
        }else{
            json.put("flag",false);
        }
        return json.toString();
    }

}
