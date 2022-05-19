package com.example.oauth2resourceserver.domain;

import com.example.oauth2resourceserver.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Role extends BaseEntity<Long> {

    public Role(String name) {
        this.name = name;
    }

    private String name;
}
