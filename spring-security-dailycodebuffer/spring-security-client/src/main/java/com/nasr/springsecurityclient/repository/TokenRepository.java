package com.nasr.springsecurityclient.repository;

import com.nasr.springsecurityclient.entity.TokenVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository  extends JpaRepository<TokenVerification,Long> {

    Optional<TokenVerification> findByToken(String token);
}
