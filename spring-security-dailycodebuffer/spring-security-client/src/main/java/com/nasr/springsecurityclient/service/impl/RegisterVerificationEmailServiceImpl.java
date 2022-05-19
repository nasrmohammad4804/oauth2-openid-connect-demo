package com.nasr.springsecurityclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class RegisterVerificationEmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email,String link){
        try {

            MimeMessage message =mailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setTo(email);
            helper.setSubject(getSubject());
            helper.setText(getContent(link));
            mailSender.send(message);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String getSubject() {
        return "here's this link for activating your account in system";
    }
    private String getContent(String link){
        return "<p>" +
                "you have register in system " +
                "click the link below for activate your account " +
                "<a href="+link+">activate account</a>"+
                "</p>";
    }
}
