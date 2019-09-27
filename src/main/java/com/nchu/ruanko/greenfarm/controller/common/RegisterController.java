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

/**
 * “注册”功能控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "common.RegisterController", description = "“注册”功能控制器")
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    /**
     * 手机短信模板
     */
    private static final String MESSAGE_TEMPLATE = "SMS_174020806";

    /**
     * 跳转至“注册成为会员/用户”页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userRegisterPage", notes = "跳转至“注册成为消费者”页面")
    @GetMapping(value = "/user/register")
    public ModelAndView userRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/user/register");
        return modelAndView;
    }

    /**
     * 跳转至“到邮箱中激活的提示”页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userRegisterWithMailActivateTipPage", notes = "跳转至“到邮箱中激活的提示”页面")
    @GetMapping("/user/register/mail/activate/tip")
    public ModelAndView userRegisterWithMailActivateTipPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/user/register-activate-tip");
        return modelAndView;
    }


    /**
     * 收集前端传来的会员/用户“注册”数据，完成校验，数据暂存在 Session 中的 tempUser，然后发送“激活账号”邮件
     *
     * @return JSON
     */
    @ApiOperation(value = "userRegisterWithMailCollectDataOperation", notes = "邮箱验证注册数据校验、发送邮件")
    @PostMapping(value = "/user/register/mail/data")
    @ResponseBody
    public String userRegisterWithMailCollectDataOperation(@RequestParam(name = "password") String password, @RequestParam(name = "mail") String mail, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (!userService.checkUniqueUserWithMail(mail)) {
            json.put("flag", false);
            json.put("reason", "您的邮箱已被注册！");
        } else {
            HttpSession session = request.getSession();
            User user = new User();
            String uid = StringUtils.createUUID();
            user.setUserUid(uid);
            user.setUserPassword(password);
            user.setUserMail(mail);
            // TODO 此处有 BUG ，因为 Session 的缘故一旦切换浏览器就无法激活，500
            session.setAttribute("tempUser", user);
            Map<String, Object> val = new HashMap<>(5);
            val.put("mail", mail);
            val.put("href", HttpUtils.getUrlPrefix(request) + "/user/register/mail/activate?uid=" + uid);
            try {
                mailService.sendThymeleafTemplateMail(mail, "【绿色农场】账户激活", "/mail/register-activate", val);
            } catch (Exception e) {
                e.printStackTrace();
                json.put("flag", false);
                json.put("reason", "邮件发送失败！system");
                return json.toString();
            }
            json.put("flag", true);
        }

        return json.toString();
    }

    /**
     * 邮件注册操作
     *
     * 到邮箱中进行“激活”操作，点击链接后的响应
     *
     * @return 页面
     */
    @ApiOperation(value = "userRegisterWithMailActivateOperation", notes = "使用邮件，激活账户")
    @GetMapping(value = "/user/register/mail/activate")
    public String userRegisterWithMailActivateOperation(@RequestParam(name = "uid") String uid, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("tempUser");
        if (user != null) {
            if (!user.getUserUid().equals(uid)) {
                // TODO 返回错误页面
                System.out.println("error");
                session.removeAttribute("tempUser");
                return null;
            } else {
                // 激活后，返回“消费者使用密码登录”界面
                userService.addUser(user);
                session.removeAttribute("tempUser");
                return "redirect:/user/password/login";
            }
        } else {
            // TODO 返回错误页面
            return null;
        }
    }

    /**
     * 发送注册时所用的手机短信验证码
     *
     * @param phone 手机号
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "sendMessageVerificationCodeOperation", notes = "发送注册时所用的手机验证码短信")
    @PostMapping(value = "/message/vcode/register")
    @ResponseBody
    public String sendMessageVerificationCodeOperation(@RequestParam(name = "phone") String phone, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String vcode = StringUtils.createVerificationCode(5);
        try {
            String responseData = ShortMessageUtils.sendVerificationCodeMessage(MESSAGE_TEMPLATE, phone, vcode);
            if (ShortMessageErrorsEnum.OK.getCode().equals(ShortMessageUtils.getErrorCode(responseData))) {
                json.put("flag", true);
                // 手机号隐藏中间四位
                json.put("phone", StringUtils.desensitizePhoneNumber(phone));
                User user = new User();
                user.setUserUid(StringUtils.createUUID());
                user.setUserPhone(phone);
                session.setAttribute("tempUser", user);
                session.setAttribute("messageVcode", vcode);

                HttpUtils.sessionAttributeInvalid(session, "tempUser", 5);
                HttpUtils.sessionAttributeInvalid(session, "messageVcode", 5);
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
        return json.toString();
    }


    /**
     * 手机短信验证码注册操作
     *
     * @param password 密码
     * @param phone 手机号
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userRegisterWithPhoneOperation", notes = "手机验证注册")
    @PostMapping(value = "/user/register/phone/operation")
    @ResponseBody
    public String userRegisterWithPhoneOperation(@RequestParam(name = "password") String password, @RequestParam(name = "phone") String phone, @RequestParam(name = "vcode") String vcode, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (!userService.checkUniqueUserWithPhone(phone)) {
            json.put("flag", false);
            json.put("reason", "您的手机号已被注册！");
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("tempUser");
            String code = (String) session.getAttribute("messageVcode");
            if (user == null || vcode == null) {
                json.put("flag", false);
                json.put("reason", "验证码已失效，请重新获取！");
            } else {
                if (!user.getUserPhone().equals(phone) || !code.equals(vcode)) {
                    json.put("flag", false);
                    json.put("reason", "验证码错误！");
                } else {
                    user.setUserPassword(password);
                    userService.addUser(user);
                    json.put("flag", true);
                    session.removeAttribute("tempUser");
                    session.removeAttribute("messageVcode");
                }
            }
        }
        return json.toString();
    }

}