package com.nasr.oauth2authorizationserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;


@Configuration
public class AuthorizationServerConfig {

    @Bean(name = "client-repository")
    public RegisteredClientRepository repository(){
        RegisteredClient registeredClient=RegisteredClient.withId(UUID.randomUUID().toString())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/app")
                .scope(OidcScopes.OPENID)
                .scope("read")
                .tokenSettings(TokenSettings.builder().setting("sso_id","mmn").build())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientId("client1")
                .clientSecret("secret1")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public ProviderSettings providerSettings(){
        return ProviderSettings.builder()
                .issuer("http://auth-server:9000")
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        RSAKey rsaKey=generateRsa();
        JWKSet jwkSet=new JWKSet(rsaKey);
        return ((jwkSelector, securityContext) ->jwkSelector.select(jwkSet));
    }

    private RSAKey generateRsa() throws NoSuchAlgorithmException {
        KeyPair keyPair=generateRsaKey();
        RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator=KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

}
