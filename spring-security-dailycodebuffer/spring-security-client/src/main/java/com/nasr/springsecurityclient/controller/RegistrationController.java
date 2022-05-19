package com.nasr.springsecurityclient.controller;

import com.nasr.springsecurityclient.model.UserModel;
import com.nasr.springsecurityclient.service.TokenVerificationService;
import com.nasr.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenVerificationService tokenService;


    @PostMapping
    public String registerUser(@RequestBody UserModel model) {
        userService.register(model);
        return "Success";
    }

    @GetMapping("/verify-registration")
    public ResponseEntity<String> verifyRegistration(@RequestParam String token) {
        tokenService.verifyToken(token);
        return ResponseEntity.ok("your account is active");
    }

    @GetMapping("/resend-verification-token/{userId}")
    public ResponseEntity<String> resendVerificationToken(@PathVariable("userId") Long userId) {
        tokenService.resendVerificationToken(userId);
        return ResponseEntity.ok("success");
    }



}
