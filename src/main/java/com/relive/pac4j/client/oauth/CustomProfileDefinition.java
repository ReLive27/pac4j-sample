package com.relive.pac4j.client.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;
import org.pac4j.core.profile.AttributeLocation;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.definition.OAuth20ProfileDefinition;

import java.util.Iterator;

/**
 * @Author ReLive
 * @Date 2021/4/2-17:30
 */
@Deprecated
public class CustomProfileDefinition extends OAuth20ProfileDefinition<CustomProfile, OAuth20Configuration> {
    public static final String USERNAME = "userName";
    public static final String ACCOUNT_NUMBER = "accountNumber";
    public static final String MAIL = "mail";
    public static final String PHONE_NUMBER = "phoneNumber";

    public CustomProfileDefinition() {
        super((x) -> {
            return new CustomProfile();
        });
        this.primary(USERNAME, Converters.STRING);
        this.primary(ACCOUNT_NUMBER, Converters.STRING);
        this.primary(MAIL, Converters.STRING);
        this.primary(PHONE_NUMBER, Converters.STRING);
    }

    @Override
    public Verb getProfileVerb() {
        return Verb.POST;
    }

    @Override
    public String getProfileUrl(OAuth2AccessToken oAuth2AccessToken, OAuth20Configuration oAuth20Configuration) {
        return "";
    }

    @Override
    public CustomProfile extractUserProfile(String body) {
        CustomProfile profile = this.newProfile(new Object[0]);
        //此处取body中用户信息
        JsonNode json = JsonHelper.getFirstNode(body).path("content");
        if (json != null) {
            json = json.path("user");
        }
        if (json != null) {
            //TODO 可以自取字段赋值
            //profile.setId(ProfileHelper.sanitizeIdentifier(profile, JsonHelper.getElement(json, "id")));
            Iterator var4 = this.getPrimaryAttributes().iterator();

            while (var4.hasNext()) {
                String attribute = (String) var4.next();
                this.convertAndAdd(profile, AttributeLocation.PROFILE_ATTRIBUTE, attribute, JsonHelper.getElement(json, attribute));
            }
        } else {
            this.raiseProfileExtractionJsonError(body);
        }

        return profile;
    }
}
