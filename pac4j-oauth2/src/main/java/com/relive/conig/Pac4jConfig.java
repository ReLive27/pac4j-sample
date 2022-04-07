package com.relive.conig;

import com.github.scribejava.core.model.Verb;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.HttpActionHelper;
import org.pac4j.oauth.client.GenericOAuth20Client;
import org.pac4j.oauth.client.GitHubClient;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.component.ComponentConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Optional;

/**
 * @author: ReLive
 * @date: 2022/3/19 10:06 下午
 */
@Configuration
@Import({ComponentConfig.class, AnnotationConfig.class})
public class Pac4jConfig {

    @Bean
    public Config config() {
        final GitHubClient gitHubClient = new GitHubClient("4b54efc462f9ced0b4ec", "08a70e5691c131e3b2c90ffa65b211f0a86f2dc6");

        final GenericOAuth20Client oAuth20Client = new GenericOAuth20Client();
        oAuth20Client.setName("GenericOAuth20Client");
        oAuth20Client.setKey("relive-client");
        oAuth20Client.setSecret("relive-client");
        oAuth20Client.setAuthUrl("http://127.0.0.1:8080/oauth2/authorize");
        oAuth20Client.setTokenUrl("http://127.0.0.1:8080/oauth2/token");
        oAuth20Client.setProfileUrl("http://127.0.0.1:8080/oauth2/userInfo");
        oAuth20Client.setClientAuthenticationMethod("basicAuth");
        oAuth20Client.setProfileVerb(Verb.GET);
        oAuth20Client.setScope("profile");
        oAuth20Client.setWithState(true);
        oAuth20Client.setSaveProfileInSession(true);
        oAuth20Client.setLogoutActionBuilder((context, sessionStore, userProfile, redirectUrl) -> {
            return Optional.of(HttpActionHelper.buildRedirectUrlAction(context, redirectUrl));
        });

        final Clients clients = new Clients("http://127.0.0.1:9090/callback", gitHubClient, oAuth20Client);
        Config config = new Config(clients);
        config.addAuthorizer("ReLive27", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        return config;
    }
}
