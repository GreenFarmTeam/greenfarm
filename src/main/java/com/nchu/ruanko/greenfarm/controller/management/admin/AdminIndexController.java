package com.nchu.ruanko.greenfarm.controller.management.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "management.admin.AdminIndexController", description = "“管理员主页”的控制器")
@Controller
public class AdminIndexController {

    @ApiOperation(value = "adminManagementIndexPage", notes = "")
    @GetMapping(value = "/greenfarm/admin/management/index")
    public ModelAndView adminManagementIndexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/admin/index");
        return modelAndView;
    }

}