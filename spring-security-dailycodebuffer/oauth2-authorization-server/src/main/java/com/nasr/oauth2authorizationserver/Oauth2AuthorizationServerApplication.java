package com.nasr.oauth2authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Oauth2AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthorizationServerApplication.class, args);
    }

}
