package com.nasr.springsecurityclient.service.impl;

import com.nasr.springsecurityclient.entity.TokenVerification;
import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.exception.TokenNotValidException;
import com.nasr.springsecurityclient.repository.TokenRepository;
import com.nasr.springsecurityclient.service.TokenVerificationService;
import com.nasr.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.nasr.springsecurityclient.service.impl.UserServiceImpl.createUrl;

@Service
@Slf4j
public class TokenVerificationServiceImpl implements TokenVerificationService {

    @Autowired
    private TokenRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterVerificationEmailServiceImpl emailService;

    @Override
    public TokenVerification save(TokenVerification token) {
        return repository.save(token);
    }

    @Override
    @Transactional
    public void verifyToken(String token) {
        Optional<TokenVerification> optionalToken = repository.findByToken(token);
        if (optionalToken.isEmpty())
            throw new TokenNotValidException("token not valid");

        if (LocalDateTime.now().isAfter(optionalToken.get().getExpirationTime())) {

            repository.delete(optionalToken.get());
            throw new TokenNotValidException("your token is expired");
        } else
            optionalToken.get().getUser().setActive(true);

    }

    @Override
    public void resendVerificationToken(Long userId) {
        User user = userService.getById(userId);

        sendResendVerificationMail(user);
    }

    private void sendResendVerificationMail(User user) {
        String token = UUID.randomUUID().toString();
        save(new TokenVerification(token, user));
        StringBuilder url = new StringBuilder(createUrl());
        url.append("?token=").append(token);
        log.info("send email with link {}", url);
        emailService.sendEmail(user.getEmail(), url.toString());
    }
}
