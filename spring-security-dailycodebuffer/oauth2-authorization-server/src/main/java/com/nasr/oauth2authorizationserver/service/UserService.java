package com.nasr.oauth2authorizationserver.service;

import com.nasr.oauth2authorizationserver.domain.User;

public interface UserService {
    User getByEmail(String email);
}
