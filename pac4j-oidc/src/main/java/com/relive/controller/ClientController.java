package com.relive.controller;

import org.pac4j.core.profile.ProfileManager;
import org.pac4j.oidc.profile.OidcProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ReLive
 * @date: 2022/3/31 9:27 下午
 */
@RestController
public class ClientController {

    @Autowired
    private ProfileManager profileManager;

    @GetMapping("/oidc")
    public OidcProfile oidc() {
        return profileManager.getProfile(OidcProfile.class).get();
    }

    @GetMapping("/default/logout")
    public String defaultLogout() {
        return "default logout";
    }
}
