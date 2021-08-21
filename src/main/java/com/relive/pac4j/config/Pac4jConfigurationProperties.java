package com.relive.pac4j.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "pac4j", ignoreUnknownFields = false)
public class Pac4jConfigurationProperties {
    private Map<String, String> clientsProperties = new LinkedHashMap<>();

    private String callbackUrl;

    private String tokenGenerate;

    private List<SecurityConstraint> securityConstraints = new ArrayList();

    public Map<String, String> getClientsProperties() {
        return clientsProperties;
    }

    public void setClientsProperties(final Map<String, String> properties) {
        this.clientsProperties = properties;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(final String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public List<SecurityConstraint> getSecurityConstraints() {
        return securityConstraints;
    }

    public void setSecurityConstraints(List<SecurityConstraint> securityConstraints) {
        this.securityConstraints = securityConstraints;
    }

    public String getTokenGenerate() {
        return tokenGenerate;
    }

    public void setTokenGenerate(String tokenGenerate) {
        this.tokenGenerate = tokenGenerate;
    }
}
