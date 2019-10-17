package com.nchu.ruanko.greenfarm.controller.management.business;

import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.constant.PageConstant;
import com.nchu.ruanko.greenfarm.pojo.entity.Order;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessOrderPageVo;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderFarmPageVo;
import com.nchu.ruanko.greenfarm.pojo.vo.OrderItemVo;
import com.nchu.ruanko.greenfarm.service.OrderFarmService;
import com.nchu.ruanko.greenfarm.service.OrderItemService;
import com.nchu.ruanko.greenfarm.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BusinessOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderFarmService orderFarmService;


    /**
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "businessManagementLoadOrdersInfo", notes = "商家加载产品订单数据")
    @GetMapping(value="/business/management/order/loadAll")
    public ModelAndView businessManagementLoadOrdersInfo(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize, HttpServletRequest request){
        ModelAndView modelAndView =new ModelAndView();
        User  user =(User)request.getSession().getAttribute("user");
        BusinessOrderPageVo businessOrderPageVo = orderService.loadAllOrdersOfBusiness(user.getUserUid(),pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE);
        modelAndView.setViewName("management/business/order-list");
        modelAndView.addObject("vo",businessOrderPageVo);
        return modelAndView;
    }

    @ApiOperation(value = "businessManagementLoadFarmOrdersInfo", notes = "商家加载农场订单数据")
    @GetMapping(value="/business/management/farm/order/loadAll")
    public ModelAndView businessManagementLoadFarmOrdersInfo(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "10") int pageSize, HttpServletRequest request){
        ModelAndView modelAndView =new ModelAndView();
        User  user =(User)request.getSession().getAttribute("user");
        OrderFarmPageVo orderFarmPageVo = orderFarmService.loadAllOrderFarmsOfBusiness(user.getUserUid(),pageNum, pageSize, PageConstant.PAGE_NAVIGATION_SIZE);
        modelAndView.setViewName("management/business/farm-order-list");
        modelAndView.addObject("vo",orderFarmPageVo);
        return modelAndView;
    }

    /*/business/management/order/getDetail/orderItem/*/
    @ApiOperation(value = "businessManagementLoadOrderItemsInfo", notes = "商家加载订单子项数据")
    @GetMapping(value="/business/management/order/getDetail/orderItem/{orderId}")
    @ResponseBody
    public String businessManagementLoadOrderItemsInfo(@PathVariable("orderId")String orderId){
        JSONObject jsonObject = new JSONObject();
        List<OrderItemVo> orderItemVoList = orderItemService.loadOrderItemsByOrderId(orderId);

        jsonObject.put("data",orderItemVoList);
        jsonObject.put("flag",true);

        return jsonObject.toString();
    }
    @ApiOperation(value = "businessManagementArrangeProducts", notes = "商家安排发货")
    @GetMapping(value="/business/management/order/arrange/products/{orderId}")
    @ResponseBody
    public String businessManagementArrangeProducts(@PathVariable("orderId")String orderId, HttpServletRequest request){
        JSONObject jsonObject =new JSONObject();
        User user = (User)request.getSession().getAttribute("user");
        if(orderService.arrangeSendProducts(orderId,user.getUserUid())){
            jsonObject.put("flag",1);

        }else{
            jsonObject.put("flag",0);
        }
        return jsonObject.toString();
    }



}
