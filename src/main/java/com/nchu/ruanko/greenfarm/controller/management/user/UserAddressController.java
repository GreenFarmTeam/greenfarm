package com.nchu.ruanko.greenfarm.controller.management.user;

import com.nchu.ruanko.greenfarm.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * “会员/用户收货地址”功能控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "management.user.UserAddressController", description = "“会员/用户收货地址”功能控制器")
@Controller
public class UserAddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 跳转至“用户/会员添加收货地址”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userAddAddressPage", notes = "跳转至“用户/会员添加收货地址”的页面")
    @GetMapping(value = "/user/management/address/add")
    public ModelAndView userAddAddressPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/add-address");
        modelAndView.addObject("provinceList", addressService.listAllProvinces());
        return modelAndView;
    }






}