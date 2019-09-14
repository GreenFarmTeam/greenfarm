package com.nchu.ruanko.greenfarm.controller.common;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "common.LogoutController", description = "“退出”功能控制器")
@Controller
public class LogoutController {

    /**
     * 消费者“退出”操作
     *
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userLogoutOperation", notes = "消费者“退出”操作")
    @GetMapping(value = "/user/logout/operation")
    @ResponseBody
    public String userLogoutOperation(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (request.getSession().getAttribute("user") != null) {
            json.put("flag", true);
            request.getSession().removeAttribute("user");
        }
        return json.toString();
    }

}
