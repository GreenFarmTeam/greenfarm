package com.nchu.ruanko.greenfarm.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.pojo.entity.Administrator;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.AdministratorService;
import com.nchu.ruanko.greenfarm.service.UserService;
import com.nchu.ruanko.greenfarm.util.http.HttpUtils;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageErrorsEnum;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageUtils;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * “登录”功能控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "common.LoginController", description = "“登录”功能控制器")
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    private static final String MESSAGE_TEMPLATE = "SMS_174020810";

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
     * 消费者“使用密码登录”操作
     *
     * @param key 会员名/手机号/邮箱
     * @param password 密码
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userLoginWithPasswordOperation", notes = "消费者“使用密码登录”操作")
    @PostMapping(value = "/user/password/login/operation")
    @ResponseBody
    public String userLoginWithPasswordOperation(@RequestParam(name = "key") String key, @RequestParam(name = "password") String password, @RequestParam(name = "vcode") String vcode, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        User user = null;
        String code = (String) session.getAttribute("graphVcode");
        if (!code.equals(vcode)) {
            json.put("flag", false);
            json.put("reason", "验证码错误！");
            session.removeAttribute("graphVcode");
        } else {
            user = userService.getUserByKeyAndPassword(key, password);
            if (user == null) {
                json.put("flag", false);
                json.put("reason", "账号或密码错误！");
                session.removeAttribute("graphVcode");
            } else {
                json.put("flag", true);
                session.removeAttribute("graphVcode");
                session.setAttribute("user", user);
            }
        }
        return json.toString();
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
        modelAndView.setViewName("common/user/login-phone");
        return modelAndView;
    }

    /**
     * 发送登录时所用的手机验证码短信
     *
     * @param phone 手机号
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "sendMessageVerificationCodeOperation", notes = "发送登录时所用的手机验证码短信")
    @PostMapping(value = "/message/vcode/login")
    @ResponseBody
    public String sendMessageVerificationCodeOperation(@RequestParam(name = "phone") String phone, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (userService.checkUniqueUserWithPhone(phone)) {
            json.put("flag", false);
            json.put("reason", "当前手机号没有被注册！");
        } else {
            HttpSession session = request.getSession();
            String vcode = StringUtils.createVerificationCode(8);
            try {
                String responseData = ShortMessageUtils.sendVerificationCodeMessage(MESSAGE_TEMPLATE, phone, vcode);
                if (ShortMessageErrorsEnum.OK.getCode().equals(ShortMessageUtils.getErrorCode(responseData))) {
                    json.put("flag", true);
                    json.put("phone", StringUtils.desensitizePhoneNumber(phone));
                    session.setAttribute("phone", phone);
                    session.setAttribute("messageVpassword", vcode);
                    HttpUtils.sessionAttributeInvalid(session, "phone", 3);
                    HttpUtils.sessionAttributeInvalid(session, "messageVpassword", 3);
                } else {
                    json.put("flag", false);
                    json.put("reason", ShortMessageUtils.getErrorDescription(responseData));
                }
            } catch (ClientException e) {
                e.printStackTrace();
                json.put("flag", false);
                json.put("reason", "短信发送失败！system");
                return json.toString();
            }

        }
        return json.toString();
    }

    /**
     * 消费者“使用手机号登录”操作
     *
     * @param phone 手机号
     * @param vcode 动态密码
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userLoginWithMobilePhoneOperation", notes = "消费者“使用手机号登录”操作")
    @PostMapping(value = "/user/mobile/login/operation")
    @ResponseBody
    public String userLoginWithMobilePhoneOperation(@RequestParam(value = "phone") String phone, @RequestParam(value = "vcode") String vcode, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("messageVpassword");
        String phoneNum = (String) session.getAttribute("phone");
        if (code == null || phoneNum == null) {
            json.put("flag", false);
            json.put("reason", "动态密码已失效，请重新获取！");
        } else {
            if (!code.equals(vcode) || !phoneNum.equals(phone)) {
                json.put("flag", false);
                json.put("reason", "动态密码错误！");
            } else {
                json.put("flag", true);
                session.setAttribute("user", userService.getUserByPhone(phone));
                session.removeAttribute("messageVpassword");
                session.removeAttribute("phone");
            }
        }
        return json.toString();
    }



//    /**
//     * 跳转至“商家使用密码登录”界面
//     *
//     * @return ModelAndView 视图
//     */
//    @ApiOperation(value = "businessLoginWithPasswordPage", notes = "跳转至“商家使用密码登录”界面")
//    @GetMapping(value = "/admin/password/login")
//    public ModelAndView businessLoginWithPasswordPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("");
//        return modelAndView;
//    }

//    /**
//     * 跳转至“商家使用手机验证码登录”界面
//     *
//     * @return ModelAndView 视图
//     */
//    @ApiOperation(value = "businessLoginWithMobilePhonePage", notes = "跳转至“商家使用手机短信验证码登录”界面")
//    @GetMapping(value = "/admin/mobile/login")
//    public ModelAndView businessLoginWithMobilePhonePage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("");
//        return modelAndView;
//    }

    /**
     * 跳转至“管理员使用密码登录”界面
     *
     * @return ModelAndView 视图
     */
    @ApiOperation(value = "administratorLoginWithPasswordPage", notes = "跳转至“管理员使用密码登录”界面")
    @GetMapping(value = "/greenfarm/admin/password/login")
    public ModelAndView administratorLoginWithPasswordPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/admin/login");
        return modelAndView;
    }

    /**
     * “管理员使用密码登录”操作
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    @ApiOperation(value = "administratorLoginWithPasswordOperation", notes = "“管理员使用密码登录”操作")
    @PostMapping(value = "/greenfarm/admin/password/login/operation")
    @ResponseBody
    public String administratorLoginWithPasswordOperation(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        Administrator administrator = administratorService.getAdministratorByUsernameAndPassword(username, password);
        if (administrator == null) {
            json.put("flag", false);
            json.put("reason", "账号或密码错误！");
        } else {
            json.put("flag", true);
            HttpSession session = request.getSession();
            session.setAttribute("admin", administrator);
        }
        return json.toString();
    }

//    /**
//     * 跳转至“管理员使用手机验证码登录”界面
//     *
//     * @return ModelAndView 视图
//     */
//    @ApiOperation(value = "administratorLoginWithMobilePhonePage", notes = "跳转至“管理员使用手机短信验证码登录”界面")
//    @GetMapping(value = "/greenfarm/admin/mobile/login")
//    public ModelAndView administratorLoginWithMobilePhonePage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("");
//        return modelAndView;
//    }








}
