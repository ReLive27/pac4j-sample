package com.relive.pac4j.credential.service;

import com.relive.pac4j.config.Pac4jConfigurationProperties;
import com.relive.pac4j.credential.TokenCredentialGenerator;
import lombok.Setter;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Setter
public class Context {

    private TokenCredentialGenerator generator;

    @Autowired
    private Pac4jConfigurationProperties properties;

    public Context() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName("com.relive.pac4j.credential.service." + "JwtCredentialGenerator");
        generator = (TokenCredentialGenerator) aClass.newInstance();
    }

    public Map<String, Object> getResult(List<CommonProfile> commonProfiles) {
        return generator.generateCredential(commonProfiles);
    }
}
