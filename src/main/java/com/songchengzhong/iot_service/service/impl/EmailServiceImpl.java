package com.songchengzhong.iot_service.service.impl;

import com.songchengzhong.iot_service.entity.User;
import com.songchengzhong.iot_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by songchengzhong on 2017/1/4.
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired(required = false)
    private SpringTemplateEngine thymeleaf;

    @Override
    public void sendRegisterCheckEmail(User user) {
        new Thread(() -> {
            MimeMessage mime = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");
                helper.setFrom("scz0216@126.com");
                helper.setTo(user.getEmail().trim());
                helper.setSubject("欢迎您注册物联网云服务，请验证邮箱");

                //创建消息
                Context context = new Context();
                context.setVariable("user", user);
                String message = thymeleaf.process("email/register", context);
                helper.setText(message, true);
                javaMailSender.send(mime);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).run();
    }
}
