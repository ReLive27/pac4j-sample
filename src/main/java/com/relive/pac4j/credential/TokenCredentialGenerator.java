package com.relive.pac4j.credential;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;

import java.util.List;
import java.util.Map;

/**
 * @Author ReLive
 * @Date 2021/4/12-21:45
 */
public interface TokenCredentialGenerator {

    Map<String, Object> generateCredential(List<CommonProfile> userProfiles);
}
