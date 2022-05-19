package com.example.oauth2resourceserver.service;

import com.example.oauth2resourceserver.domain.User;
import com.example.oauth2resourceserver.repository.UserRepository;
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
