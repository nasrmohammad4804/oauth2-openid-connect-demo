package com.nasr.springsecurityclient.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.nasr.springsecurityclient.Util;
import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.exception.TokenNotValidException;
import io.netty.handler.codec.base64.Base64Encoder;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@Slf4j
public class HelloController {

    private final RestTemplate template = new RestTemplate();

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

    @GetMapping("/users")
    public ResponseEntity<User[]> getUsers(@RequestHeader("Authorization") String auth) {
        String token = Util.getToken(auth);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        try {
            ResponseEntity<User[]> exchange = template.exchange("http://localhost:9090/api/users", HttpMethod.GET, new HttpEntity<>(httpHeaders), User[].class);
            return ResponseEntity.ok(exchange.getBody());

        } catch (Exception e) {
            throw new TokenNotValidException(e.getMessage());
        }
    }
    @GetMapping("/test")
    public String test(HttpServletRequest request){
        System.out.println(request.getHeader("authorization"));
        return "test";
    }
}
