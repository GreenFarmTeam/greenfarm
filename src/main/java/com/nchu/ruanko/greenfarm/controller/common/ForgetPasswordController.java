package com.nchu.ruanko.greenfarm.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.MailService;
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
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "common.ForgetPasswordController", description = "“忘记密码”功能控制器")
@Controller
public class ForgetPasswordController {


    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    private static final String TEMPLATE = "SMS_174020810";

    /**
     * 跳转至“会员/用户忘记密码”页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userForgetPasswordPage", notes = "跳转至会员“忘记密码”页面")
    @GetMapping("/user/forget/password")
    public ModelAndView userForgetPasswordPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/user/forget-password");
        return modelAndView;
    }

    /**
     * 验证“会员号”，验证成功后进行才能进行下一步
     *
     * @param key 会员号
     * @param vcode 验证码
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userForgetPasswordOperation", notes = "验证“会员号”，验证成功后进行才能进行下一步")
    @PostMapping(value = "/user/forget/password/operation")
    @ResponseBody
    public String userForgetPasswordOperation(@RequestParam(name = "key") String key, @RequestParam(name = "vcode") String vcode, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("graphVcode");
        if (!code.equals(vcode)) {
            json.put("flag", false);
            json.put("reason", "验证码错误！");
            session.removeAttribute("graphVcode");
        } else {
            User user = userService.getUserByKey(key);
            if (user == null) {
                json.put("flag", false);
                json.put("reason", "登录名不存在！");
                session.removeAttribute("graphVcode");
            } else {
                session.setAttribute("phone", user.getUserPhone());
                session.setAttribute("mail", user.getUserMail());
                session.setAttribute("userUid", user.getUserUid());
                json.put("flag", true);
            }
        }
        return json.toString();
    }


    /**
     * 跳转至“选择找回密码方法”的页面
     *
     * @param request HTTP 请求
     * @return ModelAndView
     */
    @ApiOperation(value = "userForgetPasswordMethodPage", notes = "跳转至“选择找回密码方法”的页面")
    @GetMapping(value = "/user/forget/password/method")
    public ModelAndView userForgetPasswordMethodPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("phone");
        String mail = (String) session.getAttribute("mail");
        if (phone != null) {
            modelAndView.addObject("phone", StringUtils.desensitizePhoneNumber(StringUtils.decodeBase64(phone)));
        }
        if (mail != null) {
            modelAndView.addObject("mail", StringUtils.desensitizeMail(StringUtils.decodeBase64(mail)));
        }
        modelAndView.setViewName("common/user/forget-password-method");
        return modelAndView;
    }

    /**
     * 发送含验证信息的邮件
     *
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userForgetPasswordSendMail", notes = "发送含验证信息的邮件")
    @GetMapping("/user/forget/password/method/mail")
    @ResponseBody
    public String userForgetPasswordSendMail(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String mail = (String) session.getAttribute("mail");
        String code = StringUtils.createVerificationCode(5);
        if (mail != null) {
            Map<String, Object> val = new HashMap<>(5);
            val.put("code", code);
            try {
                mailService.sendThymeleafTemplateMail(StringUtils.decodeBase64(mail), "【绿色农场】找回密码", "/mail/forget-password", val);
                json.put("flag", true);
                json.put("mail", StringUtils.desensitizeMail(StringUtils.decodeBase64(mail)));
                session.setAttribute("messageVcode", code);
                HttpUtils.sessionAttributeInvalid(session, "messageVcode", 10);
            } catch (MessagingException e) {
                e.printStackTrace();
                json.put("flag", false);
                json.put("reason", "邮件发送失败！system");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 跳转至“输入验证信息”的页面
     *
     * 通过邮件或手机短信验证信息都在这个页面上填写
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userForgetPasswordCheckMailPage", notes = "跳转至“输入验证信息”的页面，通过邮件或手机短信验证信息都在这个页面上填写")
    @GetMapping("/user/forget/password/method/check")
    public ModelAndView userForgetPasswordCheckPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/user/forget-password-vcode");
        return modelAndView;
    }

    /**
     * 验证信息校验
     *
     * 验证成功后才能进行重置密码的操作
     *
     * @param vcode 验证码
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userForgetPasswordCheckOperation", notes = "验证信息校验，验证成功后才能进行重置密码的操作")
    @PostMapping(value = "/user/forget/password/method/check/operation")
    @ResponseBody
    public String userForgetPasswordCheckOperation(@RequestParam(name = "vcode") String vcode, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("messageVcode");
        if (code == null) {
            json.put("flag", false);
            json.put("reason", "验证码已失效！");
        } else {
            if (!code.equals(vcode)) {
                json.put("flag", false);
                json.put("reason", "验证码错误！");
            } else {
                json.put("flag", true);
                session.removeAttribute("messageVcode");
            }
        }
        return json.toString();
    }

    /**
     * 发送含验证信息的手机短信
     *
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userForgetPasswordSendShortMessage", notes = "")
    @GetMapping(value = "/user/forget/password/method/message")
    @ResponseBody
    public String userForgetPasswordSendShortMessage(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("phone");
        String code = StringUtils.createVerificationCode(5);
        if (phone != null) {
            try {
                String responseData = ShortMessageUtils.sendVerificationCodeMessage(TEMPLATE, StringUtils.decodeBase64(phone), code);
                if (ShortMessageErrorsEnum.OK.getCode().equals(ShortMessageUtils.getErrorCode(responseData))) {
                    session.setAttribute("messageVcode", code);
                    json.put("flag", true);
                    json.put("phone", StringUtils.desensitizePhoneNumber(StringUtils.decodeBase64(phone)));
                    HttpUtils.sessionAttributeInvalid(session, "messageVcode", 10);
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
     * 跳转至“设置新密码”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userForgetPasswordModifyPasswordPage", notes = "跳转至“设置新密码”的页面")
    @GetMapping(value = "/user/forget/password/modify/password")
    public ModelAndView userForgetPasswordModifyPasswordPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/user/modify-password");
        return modelAndView;
    }

    /**
     * 设置新密码
     *
     * @param password 密码
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userForgetPasswordModifyPasswordOperation", notes = "设置新密码")
    @PostMapping(value = "/user/forget/password/modify/password/operation")
    @ResponseBody
    public String userForgetPasswordModifyPasswordOperation(@RequestParam(name = "password") String password, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String uid = (String) session.getAttribute("userUid");
        userService.modifyUserPasswordByUID(password, uid);
        session.removeAttribute("userUid");
        session.removeAttribute("phone");
        session.removeAttribute("mail");
        json.put("flag", true);
        return json.toString();
    }

}