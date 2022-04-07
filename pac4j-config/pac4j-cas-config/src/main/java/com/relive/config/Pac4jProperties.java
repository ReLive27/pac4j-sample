package com.relive.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author: ReLive
 * @date: 2022/3/29 5:34 下午
 */
@Data
@ConfigurationProperties(prefix = "pac4j")
public class Pac4jProperties {

    private String callbackUrl;

    private Map<String, String> client;
}
