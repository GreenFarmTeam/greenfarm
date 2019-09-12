package com.nchu.ruanko.greenfarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * 邮件服务
 *
 * @author Yuan Yueshun
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    private static final String DEFAULT_FROM_MAIL = "2161624113@qq.com";


    /**
     * 发送内容是以 Thymeleaf 为模板的 HTML 的邮件
     *
     * @param to 目标邮箱
     * @param subject 邮件主题
     * @param templatePath 模板路径
     * @param val 渲染到模板的值
     * @throws MessagingException 异常
     */
    public void sendThymeleafTemplateMail(String to, String subject, String templatePath, Map<String, Object> val) throws MessagingException {
        Context context = new Context();
        context.setVariables(val);
        sendHtmlMail(to, subject, templateEngine.process(templatePath, context));
    }

    /**
     * 发送内容为 HTML 的邮件
     *
     * @param to 目标邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @throws MessagingException 异常
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(DEFAULT_FROM_MAIL);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        sender.send(message);
    }



}
