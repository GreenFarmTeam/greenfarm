package com.nchu.ruanko.greenfarm.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.MailService;
import com.nchu.ruanko.greenfarm.service.UserService;
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

@Api(tags = "common.ForgetUsernameController", description = "“忘记会员号/用户名”功能控制器")
@Controller
public class ForgetUsernameController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    private static final String TEMPLATE = "SMS_174022882";

    /**
     * 跳转至“忘记会员号/用户名”界面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userForgetUsernamePage", notes = "跳转至“忘记会员号/用户名”界面")
    @GetMapping("/user/forget/username")
    public ModelAndView userForgetUsernamePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/user/forget-username");
        return modelAndView;
    }

    /**
     *
     * @param idcard 身份证号
     * @param realname 真实姓名
     * @param vcode 验证码
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userForgetUsernameOperation", notes = "“实名验证”操作（通过才有权知道会员名）")
    @PostMapping(value = "/user/forget/username/operation")
    @ResponseBody
    public String userForgetUsernameOperation(@RequestParam(name = "idcard") String idcard, @RequestParam(name = "realname") String realname, @RequestParam(name = "vcode") String vcode, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("graphVcode");
        if (!code.equals(vcode)) {
            json.put("flag", false);
            json.put("reason", "验证码错误！");
            session.removeAttribute("graphVcode");
        } else {
            session.removeAttribute("graphVcode");
            User user = userService.getUserByIdcardAndRealname(idcard, realname);
            if (user == null) {
                json.put("flag", false);
                json.put("reason", "该实名认证不存在！");
            } else {
                json.put("flag", true);
                session.setAttribute("username", user.getUserUsername());
                session.setAttribute("mail", user.getUserMail());
                session.setAttribute("phone", user.getUserPhone());
            }
        }
        return json.toString();
    }


    /**
     * 跳转至“选择找回用户名的方法”的界面
     *
     * @param request HTTP 请求
     * @return ModelAndView
     */
    @ApiOperation(value = "userForgetUsernameChooseMethodPage", notes = "跳转至“选择找回用户名的方法”界面")
    @GetMapping("/user/forget/username/method")
    public ModelAndView userForgetUsernameChooseMethodPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        modelAndView.setViewName("common/user/forget-username-method");
        String phone = (String) session.getAttribute("phone");
        String mail = (String) session.getAttribute("mail");
        if (phone != null) {
            modelAndView.addObject("phone", StringUtils.desensitizePhoneNumber(StringUtils.decodeBase64(phone)));
        }
        if (mail != null) {
            modelAndView.addObject("mail", StringUtils.desensitizeMail(StringUtils.decodeBase64(mail)));
        }
        return modelAndView;
    }

    /**
     * 通过邮箱找回会员号
     *
     * @param request HTTP 请求
     * @return 页面
     */
    @ApiOperation(value = "userForgetUsernameByMail", notes = "通过邮箱找回会员号")
    @GetMapping(value = "/user/forget/username/method/mail")
    @ResponseBody
    public String userForgetUsernameByMail(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String mail = (String) session.getAttribute("mail");
        String username = (String) session.getAttribute("username");
        if (mail != null && username != null) {
            Map<String, Object> val = new HashMap<>(5);
            val.put("username", username);
            try {
                mailService.sendThymeleafTemplateMail(StringUtils.decodeBase64(mail), "【绿色农场】会员号", "/mail/forget-username", val);
                json.put("flag", true);
                json.put("mail", StringUtils.desensitizeMail(StringUtils.decodeBase64(mail)));
            } catch (MessagingException e) {
                e.printStackTrace();
                json.put("flag", false);
                json.put("reason", "邮件发生失败！system");
                return json.toString();
            }
        }
        session.removeAttribute("username");
        session.removeAttribute("phone");
        session.removeAttribute("mail");
        return json.toString();
    }

    /**
     * 通过手机短信找回会员号
     *
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userForgetUsernameByPhone", notes = "通过手机短信找回会员号")
    @GetMapping(value = "/user/forget/username/method/phone")
    @ResponseBody
    public String userForgetUsernameByPhone(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("phone");
        String username = (String) session.getAttribute("username");
        if (phone != null && username != null) {
            JSONObject val = new JSONObject();
            val.put("code", username);
            try {
                String responseData = ShortMessageUtils.sendMessage(TEMPLATE, StringUtils.decodeBase64(phone), val);
                if (ShortMessageErrorsEnum.OK.getCode().equals(ShortMessageUtils.getErrorCode(responseData))) {
                    json.put("flag", true);
                    json.put("phone", StringUtils.desensitizePhoneNumber(StringUtils.decodeBase64(phone)));
                } else {
                    json.put("flag", false);
                    json.put("reason", ShortMessageUtils.getErrorDescription(responseData));
                }
                session.removeAttribute("username");
                session.removeAttribute("phone");
                session.removeAttribute("mail");
            } catch (ClientException e) {
                e.printStackTrace();
                session.removeAttribute("username");
                session.removeAttribute("phone");
                session.removeAttribute("mail");
                json.put("flag", false);
                json.put("reason", "短信发生失败！system");
                return json.toString();
            }
        }
        return json.toString();
    }

}