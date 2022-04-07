package com.relive.config;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.pac4j.oidc.credentials.authenticator.UserInfoOidcAuthenticator;
import org.pac4j.oidc.redirect.OidcRedirectionActionBuilder;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.component.ComponentConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: ReLive
 * @date: 2022/3/31 9:25 下午
 */
@Configuration
@Import({ComponentConfig.class, AnnotationConfig.class})
public class Pac4jConfig {

    @Bean
    public Config config() {
        final OidcConfiguration oidcConfiguration = new OidcConfiguration();
        oidcConfiguration.setClientId("relive-oidc");
        oidcConfiguration.setSecret("relive-oidc");
        oidcConfiguration.setDiscoveryURI("http://127.0.0.1:8080/.well-known/oauth-authorization-server");
        final OidcClient oidcClient = new OidcClient(oidcConfiguration);
        final Clients clients = new Clients("http://127.0.0.1:9090/callback", oidcClient);
        Config config = new Config(clients);
        config.addAuthorizer("ReLive27", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        return config;
    }
}
