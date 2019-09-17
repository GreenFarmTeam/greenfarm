package com.nchu.ruanko.greenfarm.controller.common;

import com.nchu.ruanko.greenfarm.util.graph.GraphVerificationCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 获取图片验证码
 *
 * @author woshiwo13021 CSDN
 */
@Api(tags = "common.GraphVerificationCodeController", description = "“图片验证码生成”功能控制器")
@Controller
public class GraphVerificationCodeController {

    /**
     * 生成图片验证码，并将得到的验证码以 graphVcode 存入 Session 中
     *
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @throws IOException 异常
     */
    @ApiOperation(value = "getGraphVerificationCode", notes = "前端请求该链接，获取图片验证码")
    @GetMapping("/graph/vcode")
    public void getGraphVerificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session = request.getSession();
        GraphVerificationCodeUtils utils = new GraphVerificationCodeUtils(120, 40, 4, 30);
        // 验证码的值放入 Session 中
        session.setAttribute("graphVcode", utils.getCode());
        utils.write(response.getOutputStream());
    }

}
