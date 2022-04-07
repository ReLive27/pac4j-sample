package com.relive.config;

import lombok.RequiredArgsConstructor;
import org.pac4j.config.client.PropertiesConfigFactory;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.config.Config;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.component.ComponentConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: ReLive
 * @date: 2022/3/31 6:50 下午
 */
@RequiredArgsConstructor
@Configuration
@Import({ComponentConfig.class, AnnotationConfig.class})
@EnableConfigurationProperties(Pac4jProperties.class)
public class Pac4jConfig {

    private final Pac4jProperties properties;

    @Bean
    public Config config() {
        PropertiesConfigFactory propertiesConfigFactory = new PropertiesConfigFactory(properties.getCallbackUrl(), properties.getClient());
        final Config config = propertiesConfigFactory.build();
        config.addAuthorizer("ReLive27", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        return config;
    }
}
