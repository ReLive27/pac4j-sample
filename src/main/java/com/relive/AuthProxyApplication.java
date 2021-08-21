package com.relive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author ReLive
 * @Date 2021/7/17-20:45
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AuthProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthProxyApplication.class, args);
    }
}
