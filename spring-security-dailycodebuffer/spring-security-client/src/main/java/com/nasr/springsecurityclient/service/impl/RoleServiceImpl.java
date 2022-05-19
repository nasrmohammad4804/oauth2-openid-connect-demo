package com.nasr.springsecurityclient.service.impl;

import com.nasr.springsecurityclient.entity.Role;
import com.nasr.springsecurityclient.repository.RoleRepository;
import com.nasr.springsecurityclient.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    public Role getByRoleName(String name){
        return repository.findByName(name).orElseThrow(
                () -> new NoSuchElementException("dont find role with name : "+name)
        );
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
