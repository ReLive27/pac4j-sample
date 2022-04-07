package com.relive.config;

import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.component.ComponentConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: ReLive
 * @date: 2022/3/30 9:01 上午
 */
@Configuration
@Import({ComponentConfig.class, AnnotationConfig.class})
public class Pac4jConfig {

    @Bean
    public Config config() {
        final CasConfiguration casConfiguration = new CasConfiguration("http://127.0.0.1:8443/cas/login");
        casConfiguration.setProtocol(CasProtocol.CAS30);
        final CasClient casClient = new CasClient(casConfiguration);
        final Clients clients = new Clients("http://127.0.0.1:9090/callback", casClient);
        Config config = new Config(clients);
        config.addAuthorizer("ReLive27", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        return config;
    }
}
