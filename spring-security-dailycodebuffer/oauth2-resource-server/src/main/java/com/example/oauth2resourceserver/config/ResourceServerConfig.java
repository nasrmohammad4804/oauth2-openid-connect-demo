package com.example.oauth2resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .mvcMatchers("/api/users")
                .hasAuthority("SCOPE_read")
                .mvcMatchers("/user-info")
                .authenticated();

        http.oauth2ResourceServer()
                .jwt();

        return http.build();
    }
}
