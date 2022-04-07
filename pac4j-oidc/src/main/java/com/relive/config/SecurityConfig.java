package com.relive.config;

import lombok.RequiredArgsConstructor;
import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.CallbackFilter;
import org.pac4j.springframework.security.web.LogoutFilter;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author: ReLive
 * @date: 2022/3/31 9:25 下午
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final Config config;

    @Bean
    @Order(1)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final SecurityFilter filter = new SecurityFilter(config, "OidcClient");

      return   http
                .antMatcher("/oidc/**")
                .addFilterBefore(filter, BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        final CallbackFilter callbackFilter = new CallbackFilter(config);

        //本地注销
        final LogoutFilter logoutFilter = new LogoutFilter(config, "/default/logout");
        logoutFilter.setDestroySession(true);
        logoutFilter.setLocalLogout(true);
        logoutFilter.setSuffix("/pac4jLogout");

        //第三方认证中心注销
        final LogoutFilter centralLogoutFilter = new LogoutFilter(config, "http://127.0.0.1:8080/logout");
        centralLogoutFilter.setLocalLogout(false);
        centralLogoutFilter.setCentralLogout(true);
        centralLogoutFilter.setLogoutUrlPattern("http://127.0.0.1:8080/.*");
        centralLogoutFilter.setSuffix("/pac4jCentralLogout");

        return http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(callbackFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(logoutFilter, CallbackFilter.class)
                .addFilterAfter(centralLogoutFilter, CallbackFilter.class)
                .csrf().disable()
                .logout()
                .logoutSuccessUrl("/")
                .and().build();
    }
}
