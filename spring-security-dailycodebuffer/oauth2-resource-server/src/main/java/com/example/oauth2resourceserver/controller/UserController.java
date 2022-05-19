package com.example.oauth2resourceserver.controller;

import com.example.oauth2resourceserver.domain.User;
import com.example.oauth2resourceserver.model.TokenDto;
import com.example.oauth2resourceserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /* consider user info uri in resource server but better which consider on authorization server */
    @GetMapping("/user-info")
    public TokenDto getUserInfo(Authentication authentication){

        User user = userService.getByEmail(authentication.getName());
        Map<String, Object> userInfo = ((Jwt) authentication.getPrincipal()).getClaims();
        System.out.println(userInfo);
        TokenDto dto = new TokenDto();

        dto.setExp(userInfo.get("exp").toString());
        dto.setIat(userInfo.get("iat").toString());
        dto.setNbf(userInfo.get("nbf").toString());
        dto.setIss(userInfo.get("iss").toString());
        dto.setSub(userInfo.get("sub").toString());
        dto.setSso_id(user.getId());
        dto.setAud((ArrayList<String>) userInfo.get("aud"));
        dto.setScope((ArrayList<String>)   userInfo.get("scope"));
        return dto;
    }

    /* resource which client for access to this need to have suitable scope */
    @GetMapping("/api/users")
    public User[] getUsers(Authentication authentication) {

        return new User[]{
                User.builder().firstName("mohammad").lastName("nasr").email("nasrmohammad4804@gmail.com")
                        .password("1234").build(),
                User.builder().firstName("javad").lastName("zare").email("javad@gmail.com")
                        .password("javad1234").build(),
                User.builder().firstName("taha").lastName("karimi").email("taha32@gmail.com")
                        .password("43taha12").build()

        };
    }
}
