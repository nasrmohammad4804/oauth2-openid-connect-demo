package com.nasr.springsecurityclient.service.comunication;

import com.nasr.springsecurityclient.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@FeignClient(name = "userApp", url = "http://localhost:9090")
public interface UserFeign {

    @GetMapping(value = "/api/users", consumes = APPLICATION_JSON_VALUE)
    User[] getUsers(@RequestHeader("Authorization") String auth);
}
