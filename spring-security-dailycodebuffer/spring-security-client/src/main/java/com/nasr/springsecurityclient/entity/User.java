package com.nasr.springsecurityclient.entity;

import com.nasr.springsecurityclient.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
