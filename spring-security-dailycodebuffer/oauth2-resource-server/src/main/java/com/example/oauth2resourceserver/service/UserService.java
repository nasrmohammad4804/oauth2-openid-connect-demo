package com.example.oauth2resourceserver.service;

import com.example.oauth2resourceserver.domain.User;

public interface UserService {
    User getByEmail(String email);
}
