package com.relive.pac4j.credential.service;

import com.relive.pac4j.credential.TokenCredentialGenerator;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;

import java.util.*;

public class JwtCredentialGenerator implements TokenCredentialGenerator {
    @Override
    public Map<String, Object> generateCredential(List<CommonProfile> userProfiles) {
        JwtGenerator jwtGenerator = new JwtGenerator(new SecretSignatureConfiguration("relive-code"));
        jwtGenerator.setExpirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 30));
        CommonProfile userProfile = userProfiles.get(0);
        if (userProfile != null) {
            String token = jwtGenerator.generate(userProfile);
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            return result;
        }
        return Collections.emptyMap();
    }
}
