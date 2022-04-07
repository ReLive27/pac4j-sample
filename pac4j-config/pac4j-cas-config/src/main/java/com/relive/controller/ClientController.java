package com.relive.controller;

import org.pac4j.cas.profile.CasProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ReLive
 * @date: 2022/3/31 9:26 下午
 */
@RestController
public class ClientController {

    @Autowired
    private ProfileManager profileManager;

    @GetMapping("/cas")
    public CasProfile cas() {
        return profileManager.getProfile(CasProfile.class).get();
    }
}
