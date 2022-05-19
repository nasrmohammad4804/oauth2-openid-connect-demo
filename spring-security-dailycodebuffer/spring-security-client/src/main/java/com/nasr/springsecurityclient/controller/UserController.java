package com.nasr.springsecurityclient.controller;

import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.service.comunication.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserFeign userFeign;

    @GetMapping("/users")
    public ResponseEntity<User[]> getUsers(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(userFeign.getUsers(auth));
    }
}
