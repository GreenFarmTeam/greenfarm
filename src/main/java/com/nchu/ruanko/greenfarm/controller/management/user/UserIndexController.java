package com.nchu.ruanko.greenfarm.controller.management.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * “会员/用户个人中心”的控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "management.user.UserIndexController", description = "“会员/用户个人中心”的控制器")
@Controller
public class UserIndexController {

    /**
     * 跳转至“会员/用户个人中心”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userManagementIndexPage", notes = "跳转至“会员/用户个人中心”的页面")
    @GetMapping(value = "/user/management/index")
    public ModelAndView userManagementIndexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/index");
        return modelAndView;
    }

}