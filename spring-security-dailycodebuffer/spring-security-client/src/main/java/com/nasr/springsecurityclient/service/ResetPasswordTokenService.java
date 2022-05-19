package com.nasr.springsecurityclient.service;

import com.nasr.springsecurityclient.entity.User;

import java.util.Optional;

public interface ResetPasswordTokenService {

    void saveResetPasswordToken(String email);

    void verifyToken(String token);

    Optional<User> getUserByResetPasswordToken(String token);

    void changePassword(User user, String newPassword);
}
