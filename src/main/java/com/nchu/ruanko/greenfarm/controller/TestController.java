package com.nchu.ruanko.greenfarm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping(value = "/test")
    public String staticVisitTest() {
        // TODO 访问静态资源路径测试页面
        return "test";
    }

}
