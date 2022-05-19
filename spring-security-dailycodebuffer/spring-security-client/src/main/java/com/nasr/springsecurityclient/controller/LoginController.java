package com.nasr.springsecurityclient.controller;

import com.nasr.springsecurityclient.service.comunication.UserFeign;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @GetMapping("/app-login")
    public String login(Authentication authentication) {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("app")
                .principal(authentication)
                .build();

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(request);
        assert authorizedClient != null;
        log.info(" access token value is : {} ", authorizedClient.getAccessToken().getTokenValue());
        log.info("refresh token value is : {}", authorizedClient.getRefreshToken().getTokenValue());

        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("access_token", authorizedClient.getAccessToken().getTokenValue());
        tokenInfo.put("user_name", authentication.getName());
        tokenInfo.put("refresh_token", authorizedClient.getRefreshToken().getTokenValue());
        tokenInfo.put("expiration_time", authorizedClient.getAccessToken().getExpiresAt());

        return Base64.getEncoder().encodeToString(JSONObject.toJSONString(tokenInfo).getBytes());
    }
}
