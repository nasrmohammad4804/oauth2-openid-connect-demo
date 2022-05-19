package com.nasr.springsecurityclient.entity;

import com.nasr.springsecurityclient.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
