package com.nasr.springsecurityclient.entity;

import com.nasr.springsecurityclient.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordToken extends BaseEntity<Long> {
    public static final Long EXPIRATION_TIME = 10L;
    public static final String USER_ID = "user_id";

    private String token;

    @OneToOne
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;


    private LocalDateTime expirationTime;

    public ResetPasswordToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationTime();
    }

    private LocalDateTime calculateExpirationTime() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
    }
}
