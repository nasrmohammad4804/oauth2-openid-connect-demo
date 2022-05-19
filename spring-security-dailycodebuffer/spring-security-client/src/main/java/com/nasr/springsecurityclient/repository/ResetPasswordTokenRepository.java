package com.nasr.springsecurityclient.repository;

import com.nasr.springsecurityclient.entity.ResetPasswordToken;
import com.nasr.springsecurityclient.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken,Long> {
    Optional<ResetPasswordToken> findByToken(String token);

    @Query("select r from ResetPasswordToken as r where r.token = :token")
    Optional<User> findUserByResetPasswordToken(String token);
}
