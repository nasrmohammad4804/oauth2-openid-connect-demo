package com.nasr.springsecurityclient.service.impl;

import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.event.RegistrationComplitationEvent;
import com.nasr.springsecurityclient.mapper.UserMapper;
import com.nasr.springsecurityclient.model.UserModel;
import com.nasr.springsecurityclient.repository.UserRepository;
import com.nasr.springsecurityclient.service.RoleService;
import com.nasr.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public User register(UserModel model) {

        User user = mapper.convertDtoToEntity(model);
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        user.setRole(roleService.getByRoleName("User"));

        User userEntity= repository.save(user);
        publisher.publishEvent(new RegistrationComplitationEvent(user,createUrl()));
        return userEntity;
    }

    public static String createUrl() {
        return "http://localhost:8080/register/verify-registration";

    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("dont find any user with id : " + id));
    }

    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow( () -> new NoSuchElementException("dont find any user with email : "+email));
    }

}
