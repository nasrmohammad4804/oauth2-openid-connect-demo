package com.nasr.springsecurityclient.service;

import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.model.UserModel;

public interface UserService {
    User register(UserModel model);
    User getById(Long id);

    User getByEmail(String email);
}
