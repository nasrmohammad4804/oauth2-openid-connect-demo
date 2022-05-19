package com.example.oauth2resourceserver.domain;

import com.example.oauth2resourceserver.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(name = "unique_define",columnNames = {"email"}))
public class User  extends BaseEntity<Long> {

    public static final String ROLE_ID="role_id";

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = ROLE_ID)
    private Role role;

    private boolean isActive;

    @Builder
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}

