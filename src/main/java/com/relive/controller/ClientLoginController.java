package com.relive.controller;

import com.relive.pac4j.credential.TokenCredentialGenerator;
import com.relive.pac4j.credential.service.Context;
import lombok.extern.slf4j.Slf4j;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *
 * @Author ReLive
 * @Date 2021/4/25-14:26
 */
@Slf4j
@RestController
@RequestMapping("/sso")
public class ClientLoginController {

    @Autowired
    private ProfileManager<CommonProfile> profileManager;

    @Autowired
    private Context context;

    @GetMapping("/github/login")
    public Map<String, Object> githubLogin(Map<String, Object> map) {
        List<CommonProfile> profiles = profileManager.getAll(true);
        Map<String, Object> result = context.getResult(profiles);
        return result;
    }

    @GetMapping("/oauth/login")
    public Map<String, Object> oauthLogin(Map<String, Object> map) {
        List<CommonProfile> profiles = profileManager.getAll(true);
        Map<String, Object> result = context.getResult(profiles);
        return result;
    }

    @GetMapping("/advanced/oauth/login")
    public Map<String, Object> oauth2Login(Map<String, Object> map) {
        List<CommonProfile> profiles = profileManager.getAll(true);
        Map<String, Object> result = context.getResult(profiles);
        return result;
    }

    @GetMapping("/cas/login")
    public Map<String, Object> cas(Map<String, Object> map) {
        List<CommonProfile> profiles = profileManager.getAll(true);
        Map<String, Object> result = context.getResult(profiles);
        return result;
    }

    @GetMapping("/cas2/login")
    public Map<String, Object> tianchi(Map<String, Object> map) {
        List<CommonProfile> profiles = profileManager.getAll(true);
        Map<String, Object> result = context.getResult(profiles);
        return result;
    }

    @GetMapping("/ldap/login")
    public Map<String, Object> ldap(Map<String, Object> map) {
        List<CommonProfile> profiles = profileManager.getAll(true);
        Map<String, Object> result = context.getResult(profiles);
        return result;
    }

}
