package com.relive.security.config;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.CallbackFilter;
import org.pac4j.springframework.security.web.LogoutFilter;
import org.pac4j.springframework.security.web.Pac4jEntryPoint;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    @Configuration
    @Order(1)
    public static class GitHubWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config, "GitHubClient");
            http
                    .antMatcher("/sso/github/**")
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        }
    }

    @Configuration
    @Order(2)
    public static class AdvancedOAuthWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config, "AdvancedOAuth2Client");

            http
                    .antMatcher("/sso/advanced/oauth/**")
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        }
    }

    @Configuration
    @Order(3)
    public static class OAuthWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config, "GenericOAuth20Client");

            http
                    .antMatcher("/sso/oauth/**")
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        }
    }

    @Configuration
    @Order(4)
    public static class LdapWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config, "AnonymousClient");

            http
                    .requestMatchers().antMatchers("/ldap/**")
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(new Pac4jEntryPoint(config, "FormClient"))
                    .and()
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        }
    }

    @Configuration
    @Order(5)
    public static class CasWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {
            final CallbackFilter callbackFilter = new CallbackFilter(config);
            callbackFilter.setMultiProfile(true);
            http
                    .requestMatchers().antMatchers("/sso/cas/**")
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(new Pac4jEntryPoint(config, "CasClient"))
                    .and()
                    .addFilterBefore(callbackFilter, BasicAuthenticationFilter.class);
        }
    }

    @Configuration
    @Order(6)
    public static class Cas2WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {
            final CallbackFilter callbackFilter = new CallbackFilter(config);
            callbackFilter.setMultiProfile(true);
            http
                    .requestMatchers().antMatchers("/sso/cas2/**")
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(new Pac4jEntryPoint(config, "Cas2Client"))
                    .and()
                    .addFilterBefore(callbackFilter, BasicAuthenticationFilter.class);
        }
    }


    /**

     * @Author ReLive
     * @Date 2021/1/17-15:12
     */
    @Configuration
    @Order(7)
    public static class DefaultSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        @Value("${ignore.path:/actuator/health}")
        private String ignorePath;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


        @Bean
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
        }


        /**
         * 允许匿名访问所有接口 主要是 oauth 接口
         *
         * @param http
         * @throws Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            final CallbackFilter callbackFilter = new CallbackFilter(config);
            callbackFilter.setMultiProfile(true);

            final LogoutFilter logoutFilter = new LogoutFilter(config, "/auth/logout");
            logoutFilter.setDestroySession(true);
            logoutFilter.setSuffix("/default/logout");

            /**此登出方式跳转到第三方客户端登出*/
            final LogoutFilter centralLogoutFilter = new LogoutFilter(config, "http://localhost:6001/defaulturlafterlogoutafteridp");
            centralLogoutFilter.setLocalLogout(false);
            centralLogoutFilter.setCentralLogout(true);
            centralLogoutFilter.setLogoutUrlPattern("http://localhost:6001/.*");
            centralLogoutFilter.setSuffix("/pac4jCentralLogout");

            http
                    .authorizeRequests()
                    .antMatchers(ignorePath.split(",")).permitAll()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(callbackFilter, BasicAuthenticationFilter.class)
                    .addFilterBefore(logoutFilter, CallbackFilter.class)
                    .addFilterAfter(centralLogoutFilter, CallbackFilter.class)
                    .csrf().disable()
                    .logout()
                    .logoutSuccessUrl("/")
                    .and()
                    .formLogin().loginPage("/oauth2/login").permitAll()
            ;

        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/**/*.jpeg");
        }

        @Bean
        public GrantedAuthorityDefaults grantedAuthorityDefaults() {
            //去掉ROLE_前缀
            return new GrantedAuthorityDefaults("");
        }

    }
}
