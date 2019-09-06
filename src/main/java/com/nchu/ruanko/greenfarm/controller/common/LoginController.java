package com.nchu.ruanko.greenfarm.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/password/login.htm")
    public String passwordLoginPage() {
        return "common/login.html";
    }

}
