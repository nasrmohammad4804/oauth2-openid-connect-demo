package com.nasr.springsecurityclient.init;

import com.nasr.springsecurityclient.entity.Role;
import com.nasr.springsecurityclient.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void createDefaultRole(){
        if(roleService.count()==0){
            Role role1=new Role("User");
            roleService.save(role1);
        }

    }
}
