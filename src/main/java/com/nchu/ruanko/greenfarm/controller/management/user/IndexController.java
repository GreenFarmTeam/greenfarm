package com.nchu.ruanko.greenfarm.controller.management.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * “会员/消费者个人中心”的主控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "management.user.IndexController", description = "“会员/消费者个人中心”的主控制器")
@Controller
public class IndexController {

    /**
     * 跳转至会员/消费者“个人中心”主页
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userManagementIndexPage", notes = "跳转至会员/消费者“个人中心”主页")
    @GetMapping(value = "/user/management/index")
    public ModelAndView userManagementIndexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/index");
        return modelAndView;
    }



}
