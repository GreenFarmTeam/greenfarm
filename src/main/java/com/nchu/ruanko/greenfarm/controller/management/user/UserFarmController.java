package com.nchu.ruanko.greenfarm.controller.management.user;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.pojo.vo.MemberFarmVo;
import com.nchu.ruanko.greenfarm.service.BusinessService;
import com.nchu.ruanko.greenfarm.service.FarmService;
import com.nchu.ruanko.greenfarm.service.FarmTypeService;
import com.nchu.ruanko.greenfarm.service.OrderFarmService;
import com.nchu.ruanko.greenfarm.util.http.HttpUtils;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageErrorsEnum;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageUtils;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserFarmController {
    @Autowired
    private FarmTypeService farmTypeService;
    @Autowired
    private FarmService farmService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private OrderFarmService orderFarmService;
    private static final String MESSAGE_TEMPLATE = "SMS_174020810";

    @ApiOperation(value = "userOrderFarm", notes = "会员预定功能")
    @GetMapping(value="member/management/order/farm/{farmId}")
    @ResponseBody
    public String userOrderFarm(@PathVariable("farmId") String farmId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("business",businessService.getBusinessDetailByFarmUID(farmId));

        return jsonObject.toString();
    }

    /**
     * 发送登录时所用的手机短信验证码
     *
     * @param phone 手机号
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "sendMessageVerificationCodeOperation", notes = "发送登录时所用的手机验证码短信")
    @PostMapping(value = "member/management/message/vcode")
    @ResponseBody
    public String sendMessageVerificationCodeOperation(@RequestParam(name = "phone") String phone, HttpServletRequest request) {
        JSONObject json = new JSONObject();

        HttpSession session = request.getSession();
        String vcode = StringUtils.createVerificationCode(8);
        try {
            String responseData = ShortMessageUtils.sendVerificationCodeMessage(MESSAGE_TEMPLATE, phone, vcode);
            if (ShortMessageErrorsEnum.OK.getCode().equals(ShortMessageUtils.getErrorCode(responseData))) {
                json.put("flag", true);
                json.put("phone", StringUtils.desensitizePhoneNumber(phone));

                json.put("realCode", vcode);

            } else {
                json.put("flag", false);
                json.put("reason", ShortMessageUtils.getErrorDescription(responseData));
            }
        } catch (ClientException e) {
            e.printStackTrace();
            json.put("flag", false);
            json.put("reason", "短信发送失败！system");
            return json.toString();
        }


        return json.toString();
    }

    /**
     * 提交预约看场订单
     */
    @ApiOperation(value = "submitOrderFarm", notes = "提交预约看场的定单")
    @PostMapping(value="member/management/order/farm/submit")
    @ResponseBody
    public  String submitOrderFarm(@RequestParam("name")String name,@RequestParam("phone")String phone,@RequestParam("orderDate")String orderDate,@RequestParam("requireInfo")String requireInfo,@RequestParam("farmId")String farmId, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession();
        System.out.println("name"+name);
        User user = (User)session.getAttribute("user");
        if(farmService.submitOrderFarm(user.getUserUid(),farmId,name,phone,orderDate,requireInfo))
            jsonObject.put("flag", true);
        else
        {
            jsonObject.put("flag", false);
        }
        return jsonObject.toString();
    }

    /**
     * 根据预约信息得到商家以及农场的信息
     */
    @ApiOperation(value = "getFarmDetailInfoByOrderFarm", notes = "根据预约信息得到商家以及农场的信息")
    @GetMapping(value = "/member/management/orderFarm/load/{orderFarmUid}")
    @ResponseBody
    public String getFarmDetailInfoByOrderFarm(@PathVariable(value = "orderFarmUid") String orderFarmUid){
        JSONObject jsonObject = new JSONObject();
        MemberFarmVo farmDetailInfo  = orderFarmService.getFarmDetailInfoByOrderFarm(orderFarmUid);
        jsonObject.put("farm",farmDetailInfo);
        jsonObject.put("flag", 1);

        return  jsonObject.toString();

    }


}
