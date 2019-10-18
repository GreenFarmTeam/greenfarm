package com.nchu.ruanko.greenfarm.controller.management.user;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.service.MailService;
import com.nchu.ruanko.greenfarm.service.UserService;
import com.nchu.ruanko.greenfarm.util.http.CertificationUtils;
import com.nchu.ruanko.greenfarm.util.http.HttpUtils;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageErrorsEnum;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageUtils;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * “会员/用户个人基本信息管理”功能控制器
 *
 * @author Yuan Yueshun
 */
@Api(tags = "management.user.UserBasicInformationController", description = "“会员/用户个人基本信息管理”功能控制器")
@Controller
public class UserBasicInformationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    private static final String MESSAGE_TEMPLATE = "SMS_174020806";

    /**
     * 跳转至会员/用户“修改昵称”页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userModifyNicknamePage", notes = "跳转至会员/消费者“修改昵称”页面")
    @GetMapping(value = "/user/management/nickname")
    public ModelAndView userModifyNicknamePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/modify-nickname");
        return modelAndView;
    }


    /**
     * 会员/用户“修改昵称”操作
     *
     * @param nickname 昵称
     * @param uid 会员/用户 UID
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userModifyNicknameOperation", notes = "会员/用户“修改昵称”操作")
    @PostMapping(value = "/user/management/nickname/operation/{uid}")
    @ResponseBody
    public String userModifyNicknameOperation(@RequestParam(name = "nickname") String nickname, @PathVariable(name = "uid") String uid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        userService.modifyUserNicknameByUID(nickname, uid);
        json.put("flag", true);
        User user = userService.getUserByUID(uid);
        // 修改后，及时更新 Session 中的 user
        session.setAttribute("user", user);
        return json.toString();
    }

    /**
     * 跳转至“会员/用户实名认证”页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userCertificationPage", notes = "跳转至“会员/用户实名认证”页面")
    @GetMapping(value = "/user/management/certification")
    public ModelAndView userCertificationPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/certification");
        if (user.getUserIdcard() != null) {
            modelAndView.addObject("name", StringUtils.desensitizeName(StringUtils.decodeBase64(user.getUserRealname())));
            modelAndView.addObject("idcard", StringUtils.desensitizeIdCard(StringUtils.decodeBase64(user.getUserIdcard())));
        }
        return modelAndView;
    }

    /**
     * 会员/用户“实名认证”操作
     *
     * @param realname 真实姓名
     * @param idcard 身份证号
     * @param uid UID
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userCertificationOperation", notes = "会员/用户“实名认证”操作")
    @PostMapping(value = "/user/management/certification/operation/{uid}")
    @ResponseBody
    public String userCertificationOperation(@RequestParam(name = "realname") String realname, @RequestParam(name = "idcard") String idcard, @PathVariable(name = "uid") String uid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (!userService.checkUniqueUserWithIdcard(idcard)) {
            json.put("flag", false);
            json.put("reason", "当前身份证已被认证！");
        } else {
            try {
                System.out.println("realname"+realname);
                boolean successFlag = CertificationUtils.certification(realname, idcard);
                if (successFlag) {

                    userService.modifyUserRealnameAndIdcardByUID(realname, idcard, uid);
                    json.put("flag", true);
                    HttpSession session = request.getSession();
                    User user = userService.getUserByUID(uid);
                    // 修改后，及时更新 Session 中的 user
                    session.setAttribute("user", user);
                } else {
                    json.put("flag", false);
                    json.put("reason", "实名认证失败！");
                }
            } catch (Exception e) {
                json.put("flag", false);
                json.put("reason", "实名认证失败！system");
                e.printStackTrace();
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 跳转至“会员/用户绑定邮箱”界面
     *
     * @param request HTTP 请求
     * @return ModelAndView
     */
    @ApiOperation(value = "userModifyMailPage", notes = "跳转至“会员/用户绑定邮箱”界面")
    @GetMapping(value = "/user/management/mail")
    public ModelAndView userModifyMailPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/modify-mail");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user.getUserMail() != null) {
            modelAndView.addObject("mail", StringUtils.desensitizeMail(StringUtils.decodeBase64(user.getUserMail())));
        }
        return modelAndView;
    }

    /**
     * 收集前端数据，发送邮件
     *
     * @param mail 邮箱
     * @param uid UID
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userModifyMailCollectDataOperation", notes = "收集前端数据，发送邮件")
    @PostMapping(value = "/user/management/mail/data")
    @ResponseBody
    public String userModifyMailCollectDataOperation(@RequestParam(name = "mail") String mail, @RequestParam(name = "uid") String uid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (!userService.checkUniqueUserWithMail(mail)) {
            json.put("flag", false);
            json.put("reason", "当前邮箱已被使用！");
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            Map<String, Object> val = new HashMap<>(5);
            val.put("username", user.getUserUsername());
            val.put("href", HttpUtils.getUrlPrefix(request) + "/user/management/mail/bind/" + uid);
            try {
                mailService.sendThymeleafTemplateMail(mail, "【绿色农场】确认邮箱绑定", "/mail/mail-bind", val);
                session.setAttribute("mail", mail);
                json.put("flag", true);
            } catch (MessagingException e) {
                json.put("flag", false);
                json.put("reason", "邮件发送失败！system");
                e.printStackTrace();
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 跳转至“前往邮箱确认绑定提示”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userModifyMailBindTipPage", notes = "跳转至“前往邮箱确认绑定提示”的页面")
    @GetMapping("/user/management/mail/bind/tip")
    public ModelAndView userModifyMailBindTipPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/mail-bind-tip");
        return modelAndView;
    }

    /**
     * 确认绑定邮箱操作
     *
     * @param uid UID
     * @param request HTTP 请求
     * @return 页面
     */
    @ApiOperation(value = "userModifyMailBindOperation", notes = "确认绑定邮箱操作")
    @GetMapping(value = "/user/management/mail/bind/{uid}")
    public String userModifyMailBindOperation(@PathVariable(name = "uid") String uid, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String mail = (String) session.getAttribute("mail");
        User user = (User) session.getAttribute("user");
        if (!user.getUserUid().equals(uid)) {
            // TODO 返回错误界面
            System.out.println("error");
            session.removeAttribute("mail");
            return null;
        } else {
            userService.modifyUserMailByUID(mail, uid);
            session.removeAttribute("mail");
            // 修改后，及时更新 Session 中的 user
            session.setAttribute("user", userService.getUserByUID(uid));
            return "redirect:/user/management/index";
        }
    }

    /**
     * 跳转至“绑定手机”的页面
     *
     * @param request HTTP 请求
     * @return ModelAndView
     */
    @ApiOperation(value = "userModifyPhonePage", notes = "跳转至“绑定手机”的页面")
    @GetMapping(value = "/user/management/phone")
    public ModelAndView userModifyPhonePage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        modelAndView.setViewName("management/user/modify-phone");
        if (user.getUserPhone() != null) {
            modelAndView.addObject("phone", StringUtils.desensitizePhoneNumber(StringUtils.decodeBase64(user.getUserPhone())));
        }
        return modelAndView;
    }


    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "sendMessageVerificationCodeOperation", notes = "发送手机验证码")
    @PostMapping(value = "/message/vcode/bind")
    @ResponseBody
    public String sendMessageVerificationCodeOperation(@RequestParam(name = "phone") String phone, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        String vcode = StringUtils.createVerificationCode(5);
        try {
            String responseData = ShortMessageUtils.sendVerificationCodeMessage(MESSAGE_TEMPLATE, phone, vcode);
            if (ShortMessageErrorsEnum.OK.getCode().equals(ShortMessageUtils.getErrorCode(responseData))) {
                json.put("flag", true);
                json.put("phone", StringUtils.desensitizePhoneNumber(phone));
                session.setAttribute("phone", phone);
                session.setAttribute("messageVcode", vcode);
                HttpUtils.sessionAttributeInvalid(session, "phone", 5);
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
     * 修改绑定手机号操作
     *
     * @param phone 手机号
     * @param vcode 验证码
     * @param uid UID
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userModifyPhoneOperation", notes = "修改绑定手机号操作")
    @PostMapping(value = "/user/management/phone/operation/{uid}")
    @ResponseBody
    public String userModifyPhoneOperation(@RequestParam(name = "phone") String phone, @RequestParam(name = "vcode") String vcode, @PathVariable(name = "uid") String uid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (!userService.checkUniqueUserWithPhone(phone)) {
            json.put("flag", false);
            json.put("reason", "手机号已被绑定过！");
        } else {
            HttpSession session = request.getSession();
            String phoneNum = (String) session.getAttribute("phone");
            String code = (String) session.getAttribute("messageVcode");
            if (phoneNum == null || code == null) {
                json.put("flag", false);
                json.put("reason", "验证码已失效，请重新获取！");
            } else {
                if (!phoneNum.equals(phone) || !code.equals(vcode)) {
                    json.put("flag", false);
                    json.put("reason", "验证码错误！");
                } else {
                    json.put("flag", true);
                    userService.modifyUserPhoneByUID(phone, uid);
                    session.setAttribute("user", userService.getUserByUID(uid));
                    session.removeAttribute("phone");
                    session.removeAttribute("messageVcode");
                }
            }
        }
        return json.toString();
    }

    /**
     * 跳转至“修改密码”的页面
     *
     * @return ModelAndView
     */
    @ApiOperation(value = "userModifyPasswordPage", notes = "跳转至“修改密码”的页面")
    @GetMapping(value = "/user/management/password")
    public ModelAndView userModifyPasswordPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("management/user/modify-password");
        return modelAndView;
    }


    /**
     * “修改密码”操作
     *
     * @param password 密码
     * @param uid UID
     * @param request HTTP 请求
     * @return JSON
     */
    @ApiOperation(value = "userModifyPasswordOperation", notes = "“修改密码”操作")
    @PostMapping(value = "/user/management/password/operation/{uid}")
    @ResponseBody
    public String userModifyPasswordOperation(@RequestParam(name = "password") String password, @PathVariable(name = "uid") String uid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        userService.modifyUserPasswordByUID(password, uid);
        json.put("flag", true);
        session.setAttribute("user",userService.getUserByUID(uid));
        return json.toString();
    }

}