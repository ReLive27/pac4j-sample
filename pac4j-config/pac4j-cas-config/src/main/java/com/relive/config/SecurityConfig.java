package com.relive.config;

import lombok.RequiredArgsConstructor;
import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.CallbackFilter;
import org.pac4j.springframework.security.web.LogoutFilter;
import org.pac4j.springframework.security.web.Pac4jEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author: ReLive
 * @date: 2022/3/30 9:01 上午
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final Config config;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final CallbackFilter callbackFilter = new CallbackFilter(config);

        //本地注销
        final LogoutFilter logoutFilter = new LogoutFilter(config, "/default/logout");
        logoutFilter.setDestroySession(true);
        logoutFilter.setLocalLogout(true);
        logoutFilter.setSuffix("/pac4jLogout");

        //第三方认证中心注销
        final LogoutFilter centralLogoutFilter = new LogoutFilter(config, "http://127.0.0.1:8443/cas/logout");
        centralLogoutFilter.setLocalLogout(false);
        centralLogoutFilter.setCentralLogout(true);
        centralLogoutFilter.setLogoutUrlPattern("http://127.0.0.1:8443/.*");
        centralLogoutFilter.setSuffix("/pac4jCentralLogout");

        return http
                .authorizeRequests()
                .antMatchers("/cas/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(new Pac4jEntryPoint(config, "CasClient"))
                .and()
                .addFilterBefore(callbackFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(logoutFilter, CallbackFilter.class)
                .addFilterAfter(centralLogoutFilter, CallbackFilter.class)
                .csrf().disable()
                .logout()
                .logoutSuccessUrl("/").and().build();
    }
}
