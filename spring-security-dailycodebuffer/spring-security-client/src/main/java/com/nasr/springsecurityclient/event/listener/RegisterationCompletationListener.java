package com.nasr.springsecurityclient.event.listener;

import com.nasr.springsecurityclient.entity.TokenVerification;
import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.event.RegistrationComplitationEvent;
import com.nasr.springsecurityclient.service.TokenVerificationService;
import com.nasr.springsecurityclient.service.impl.RegisterVerificationEmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegisterationCompletationListener {

    @Autowired
    private TokenVerificationService tokenService;

    @Autowired
    private RegisterVerificationEmailServiceImpl emailService;

    @EventListener
    public void sendEmail(RegistrationComplitationEvent event) {
        //create verification token for link
        //send email

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.save(new TokenVerification(token, user));
        StringBuilder url = new StringBuilder(event.getUrl());
        url.append("?token=").append(token);
        log.info("send email with link {}",url);
        emailService.sendEmail(event.getUser().getEmail(), url.toString());

    }
}
