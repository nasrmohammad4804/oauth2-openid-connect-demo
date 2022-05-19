package com.nasr.springsecurityclient.service.impl;

import com.nasr.springsecurityclient.entity.ResetPasswordToken;
import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.exception.TokenNotValidException;
import com.nasr.springsecurityclient.repository.ResetPasswordTokenRepository;
import com.nasr.springsecurityclient.service.ResetPasswordTokenService;
import com.nasr.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    @Autowired
    private ResetPasswordTokenRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ResetPasswordEmailServiceImpl resetPasswordEmailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveResetPasswordToken(String email) {
        User user = userService.getByEmail(email);
        String token= UUID.randomUUID().toString();
        repository.save(new ResetPasswordToken(token,user));
        sentResetPasswordMail(user,token);
    }

    @Override
    @Transactional
    public void verifyToken(String token) {
        Optional<ResetPasswordToken> resetPasswordToken = repository.findByToken(token);
        if (resetPasswordToken.isEmpty())
            throw new TokenNotValidException("resetPassword token not valid");

        if (LocalDateTime.now().isAfter(resetPasswordToken.get().getExpirationTime())){
            repository.delete(resetPasswordToken.get());
            throw new TokenNotValidException("this token is expired");
        }

    }

    @Override
    public Optional<User> getUserByResetPasswordToken(String token) {
        return repository.findUserByResetPasswordToken(token);
    }

    @Override
    @Transactional
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    private void sentResetPasswordMail(User user, String token)  {
        resetPasswordEmailService.sendEmail(user.getEmail(),getResetPasswordLink(token));
    }
    private String getResetPasswordLink(String token){
        return "http://localhost:8080/savePassword?token="+token;
    }
}
