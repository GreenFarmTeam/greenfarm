package com.nchu.ruanko.greenfarm.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * “登录”功能控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "common.LoginController", description = "“登录”功能控制器")
@Controller
public class LoginController {

    /**
     * 跳转至“消费者使用密码登录”界面
     *
     * @return ModelAndView 视图
     */
    @ApiOperation(value = "userLoginWithPasswordPage", notes = "跳转至“消费者使用密码登录”界面")
    @GetMapping(value = "/user/password/login")
    public ModelAndView userLoginWithPasswordPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/user/login");
        return modelAndView;
    }

    /**
     * 跳转至“消费者使用手机验证码登录”界面
     *
     * @return ModelAndView 视图
     */
    @ApiOperation(value = "userLoginWithMobilePhonePage", notes = "跳转至“消费者使用手机短信验证码登录”界面")
    @GetMapping(value = "/user/mobile/login")
    public ModelAndView userLoginWithMobilePhonePage() {
        ModelAndView modelAndView = new ModelAndView();
        // TODO
        modelAndView.setViewName("");
        return modelAndView;
    }


    /**
     * 跳转至“商家使用密码登录”界面
     *
     * @return ModelAndView 视图
     */
    @ApiOperation(value = "businessLoginWithPasswordPage", notes = "跳转至“商家使用密码登录”界面")
    @GetMapping(value = "/business/password/login")
    public ModelAndView businessLoginWithPasswordPage() {
        ModelAndView modelAndView = new ModelAndView();
        // TODO
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 跳转至“商家使用手机验证码登录”界面
     *
     * @return ModelAndView 视图
     */
    @ApiOperation(value = "businessLoginWithMobilePhonePage", notes = "跳转至“商家使用手机短信验证码登录”界面")
    @GetMapping(value = "/business/mobile/login")
    public ModelAndView businessLoginWithMobilePhonePage() {
        ModelAndView modelAndView = new ModelAndView();
        // TODO
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 跳转至“管理员使用密码登录”界面
     *
     * @return ModelAndView 视图
     */
    @ApiOperation(value = "administratorLoginWithPasswordPage", notes = "跳转至“管理员使用密码登录”界面")
    @GetMapping(value = "/greenfarm/admin/password/login")
    public ModelAndView administratorLoginWithPasswordPage() {
        ModelAndView modelAndView = new ModelAndView();
        // TODO
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 跳转至“管理员使用手机验证码登录”界面
     *
     * @return ModelAndView 视图
     */
    @ApiOperation(value = "administratorLoginWithMobilePhonePage", notes = "跳转至“管理员使用手机短信验证码登录”界面")
    @GetMapping(value = "/greenfarm/admin/mobile/login")
    public ModelAndView administratorLoginWithMobilePhonePage() {
        ModelAndView modelAndView = new ModelAndView();
        // TODO
        modelAndView.setViewName("");
        return modelAndView;
    }








}
