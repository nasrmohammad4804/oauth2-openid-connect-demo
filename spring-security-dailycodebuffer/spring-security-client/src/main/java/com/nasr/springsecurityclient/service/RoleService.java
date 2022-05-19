package com.nasr.springsecurityclient.service;

import com.nasr.springsecurityclient.entity.Role;

public interface RoleService {
    Role getByRoleName(String name);

    Role save(Role role);
    long count();
}
