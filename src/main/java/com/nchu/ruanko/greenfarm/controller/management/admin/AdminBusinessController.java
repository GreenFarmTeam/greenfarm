package com.nchu.ruanko.greenfarm.controller.management.admin;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "management.admin.AdminBusinessController", description = "")
@Controller
public class AdminBusinessController {


    @GetMapping(value = "/greenfarm/admin/management/business/review")
    public ModelAndView adminReviewBusinessPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("");
        modelAndView.addObject("",  "");
        return modelAndView;
    }


}
