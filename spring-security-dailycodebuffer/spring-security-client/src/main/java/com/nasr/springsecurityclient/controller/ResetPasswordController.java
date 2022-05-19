package com.nasr.springsecurityclient.controller;

import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.model.PasswordModel;
import com.nasr.springsecurityclient.service.ResetPasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class ResetPasswordController {

    @Autowired
    private ResetPasswordTokenService service;

    @GetMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email){

        service.saveResetPasswordToken(email);
        return ResponseEntity.ok("success");
    }


    @PostMapping("/savePassword")
    public String savePassword(@RequestParam String token, @RequestBody PasswordModel passwordModel){
        service.verifyToken(token);
        Optional<User> user = service.getUserByResetPasswordToken(token);
        if (user.isPresent()){
            service.changePassword(user.get(),passwordModel.getNewPassword());
            return "password reset successfully";
        }
        else {
            return "not valid token";
        }

    }
}
