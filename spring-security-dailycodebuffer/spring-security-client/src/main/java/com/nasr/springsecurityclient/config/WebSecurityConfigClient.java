package com.nasr.springsecurityclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfigClient {

    private static final String[] PERMIT_ENDPOINTS = {"/hello/**", "/register/**", "/register"};

    @Autowired
    private JwtAuthenticationFilter filter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

/*
        http.authorizeHttpRequests()
                .mvcMatchers(PERMIT_ENDPOINTS)
                .permitAll()
                .anyRequest()
                .authenticated();*/

        http.authorizeHttpRequests()
                .mvcMatchers("/app-login")
                        .authenticated()
                                .anyRequest()
                                        .permitAll();

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(filter, OAuth2LoginAuthenticationFilter.class);

        http.oauth2Login(oauth2Login -> {
            oauth2Login.loginPage("/oauth2/authorization/app");
//                    oauth2Login.defaultSuccessUrl("/hello");
        });
//                .oauth2Client(Customizer.withDefaults());
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
