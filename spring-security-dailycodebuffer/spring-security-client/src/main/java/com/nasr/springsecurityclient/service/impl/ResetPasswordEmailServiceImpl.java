package com.nasr.springsecurityclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class ResetPasswordEmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email, String link) {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(getSubject());
            helper.setTo(email);
            helper.setText(getContent(link));
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }


    }

    private String getContent(String link) {
        return "<h2>for changing your password click on below</h2>" +
                "<a href=" + link + ">change password</a>";
    }

    private String getSubject() {
        return "<p>here this link for reset your password in system</p>";
    }
}
