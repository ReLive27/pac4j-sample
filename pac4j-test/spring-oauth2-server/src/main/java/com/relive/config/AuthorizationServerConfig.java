package com.relive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.util.UUID;

/**
 * @author: ReLive
 * @date: 2021/12/13 12:18 下午
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient oauth2RegisteredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("relive-client")
                .clientSecret(passwordEncoder.encode("relive-client"))
                .clientAuthenticationMethods(s -> {
                    s.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
                    s.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
                })
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .redirectUri("http://127.0.0.1:9090/callback?client_name=GenericOAuth20Client")
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)//requireAuthorizationConsent：是否需要授权统同意
                        .requireProofKey(false)//requireProofKey：是否需要证明密钥
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)//idTokenSignatureAlgorithm：签名算法
                        .accessTokenTimeToLive(Duration.ofSeconds(30 * 60))//accessTokenTimeToLive：access_token有效期
                        .refreshTokenTimeToLive(Duration.ofSeconds(60 * 60))//refreshTokenTimeToLive：refresh_token有效期
                        .reuseRefreshTokens(true)//reuseRefreshTokens：是否重用刷新令牌
                        .build())
                .build();

        RegisteredClient oidcRegisteredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("relive-oidc")
                .clientSecret(passwordEncoder.encode("relive-oidc"))
                .clientAuthenticationMethods(s -> {
                    s.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
                    s.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
                })
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .redirectUri("http://127.0.0.1:9090/callback?client_name=OidcClient")
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.OPENID)
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)//requireAuthorizationConsent：是否需要授权统同意
                        .requireProofKey(false)//requireProofKey：是否需要证明密钥
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)//idTokenSignatureAlgorithm：签名算法
                        .accessTokenTimeToLive(Duration.ofSeconds(30 * 60))//accessTokenTimeToLive：access_token有效期
                        .refreshTokenTimeToLive(Duration.ofSeconds(60 * 60))//refreshTokenTimeToLive：refresh_token有效期
                        .reuseRefreshTokens(true)//reuseRefreshTokens：是否重用刷新令牌
                        .build())
                .build();

        InMemoryRegisteredClientRepository registeredClientRepository = new InMemoryRegisteredClientRepository(oauth2RegisteredClient,oidcRegisteredClient);
        return registeredClientRepository;
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("http://127.0.0.1:8080")
                .build();
    }

}

