package com.congnghejava.service.impl;

import com.congnghejava.service.EmailService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    public boolean sendEmailForgotPassword(String to, Map<String, Object> model) {
        MimeMessage message = sender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // add attachment
//            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(to);
            helper.setText(html, true);
            helper.setSubject("Khôi phục mật khẩu");
            helper.setFrom("datnquit@gmail.com");
            sender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }
}
