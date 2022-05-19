package com.nasr.springsecurityclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasr.springsecurityclient.model.TokenDto;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String USER_INFO_URL = "http://localhost:9090/user-info";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER="Bearer ";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AUTHORIZATION);

        if (authorization != null && authorization.startsWith(BEARER)) {
            String token = authorization.replace(BEARER, "");
            try {

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(AUTHORIZATION, BEARER + token);

                ResponseEntity<TokenDto> exchange = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, new HttpEntity<>(httpHeaders), TokenDto.class);
                TokenDto tokenDto = exchange.getBody();
                assert tokenDto != null;
                List<SimpleGrantedAuthority> authorities = tokenDto.getScope().stream().map(SimpleGrantedAuthority::new).toList();
                System.out.println(tokenDto);
                OAuth2User oAuth2User = new DefaultOAuth2User(authorities, Map.of("exp", tokenDto.getExp(), "sub", tokenDto.getSub(), "sso_id", tokenDto.getSso_id()), "sso_id");
                SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(oAuth2User, authorities, "app"));
                filterChain.doFilter(request, response);

            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(new ObjectMapper().writeValueAsString("invalid token"));
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(new ObjectMapper().writeValueAsString("need to token for authentication"));
        }
        ;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)  {
        return request.getServletPath().startsWith("/app-login");
    }
}
