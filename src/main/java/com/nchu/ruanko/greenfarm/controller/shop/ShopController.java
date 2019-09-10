package com.nchu.ruanko.greenfarm.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    @GetMapping(value = "/index")
    public String indexPage() {
        return "shop/index";
    }

}