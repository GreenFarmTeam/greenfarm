package com.nchu.ruanko.greenfarm.controller.common;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = "common.AddressController", description = "“省市县地址联动”功能控制器")
@Controller
public class AddressController {

    @GetMapping(value = "/address/level")
    @ResponseBody
    public String getNextLevelAddress(@RequestParam(name = "pid") String parentLevelId) {

        return "";
    }

}
