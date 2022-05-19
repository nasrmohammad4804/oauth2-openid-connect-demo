package com.nasr.oauth2authorizationserver.service;

import com.nasr.oauth2authorizationserver.domain.User;
import com.nasr.oauth2authorizationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("dont find any user with email : " + email));
    }

}
