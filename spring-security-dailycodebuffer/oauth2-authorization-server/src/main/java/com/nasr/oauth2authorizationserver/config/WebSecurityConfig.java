package com.nasr.oauth2authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .anyRequest()
                .authenticated();

        http.formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Autowired
    public void bindAuthenticationProvider(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean(name = "customPasswordEncoder")
    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder(20);
        return NoOpPasswordEncoder.getInstance();
    }
}
