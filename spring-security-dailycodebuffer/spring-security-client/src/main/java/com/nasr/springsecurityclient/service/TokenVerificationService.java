package com.nasr.springsecurityclient.service;

import com.nasr.springsecurityclient.entity.TokenVerification;

public interface TokenVerificationService {

    TokenVerification save(TokenVerification token);

    void verifyToken(String token);

    void resendVerificationToken(Long userId);
}
