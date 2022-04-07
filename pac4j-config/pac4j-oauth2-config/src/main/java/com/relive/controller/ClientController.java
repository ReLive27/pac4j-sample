package com.relive.controller;

import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.profile.OAuth20Profile;
import org.pac4j.oauth.profile.github.GitHubProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ReLive
 * @date: 2022/3/20 1:43 下午
 */
@RestController
public class ClientController {

    @Autowired
    private ProfileManager profileManager;

    @GetMapping("/github")
    public GitHubProfile github() {
        return profileManager.getProfile(GitHubProfile.class).get();
    }

    @GetMapping("/generic/oauth")
    public OAuth20Profile genericOAuth2() {
        return profileManager.getProfile(OAuth20Profile.class).get();
    }


    @GetMapping("/default/logout")
    public String defaultLogout() {
        return "default logout";
    }
}
