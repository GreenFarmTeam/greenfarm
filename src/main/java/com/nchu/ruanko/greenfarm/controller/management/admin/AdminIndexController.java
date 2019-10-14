package com.nchu.ruanko.greenfarm.controller.management.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "management.admin.AdminIndexController", description = "“管理员主页”的控制器")
@Controller
public class AdminIndexController {

    /**
     * 跳转至“管理员主页”
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "adminManagementIndexPage", notes = "跳转至“管理员主页”")
    @GetMapping(value = "/greenfarm/admin/management/index")
    public ModelAndView adminManagementIndexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/index");
        return modelAndView;
    }

    @GetMapping(value = "/greenfarm/admin/management/welcome")
    public ModelAndView adminManagementWelcomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/welcome");
        return modelAndView;
    }

}