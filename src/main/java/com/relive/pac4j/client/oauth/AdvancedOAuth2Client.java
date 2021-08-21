package com.relive.pac4j.client.oauth;

import com.github.scribejava.core.model.Verb;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.pac4j.core.profile.converter.AttributeConverter;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.profile.generic.GenericOAuth20ProfileDefinition;

import java.util.Iterator;
import java.util.Map;

/**
 * @Author ReLive
 * @Date 2021/5/7-17:47
 */
@Slf4j
@Data
public class AdvancedOAuth2Client extends OAuth20Client {

    public enum AdvancedScope {
        ALL,
        USER,
        EMAIL,
    }

    private AdvancedScope scope;
    private String scopeValue;
    private String authUrl;
    private String tokenUrl;
    private String clientAuthenticationMethod;
    private String tokenName;
    private String tokenExtractMethod;
    private String profileUrl;
    private String profilePath;
    private String profileId;
    private Verb profileVerb;
    private boolean tokenAsHeader;
    private boolean withState;
    private String tokenParamName;

    public AdvancedOAuth2Client(final String key, final String secret) {
        this(key, secret, AdvancedScope.ALL.toString().toLowerCase());
    }

    public AdvancedOAuth2Client(final String key, final String secret, final String scopeValue) {
        setKey(key);
        setSecret(secret);
        this.scopeValue = scopeValue;
        this.tokenAsHeader = true;
        this.withState = true;
    }

    @Override
    protected void clientInit() {
        log.info("Initializing oauth client...");
        final AdvancedApi20 advancedApi20 = new AdvancedApi20(this.authUrl, this.tokenUrl);
        configuration.setApi(advancedApi20);
        if (this.clientAuthenticationMethod != null) {
            advancedApi20.setClientAuthenticationMethod(clientAuthenticationMethod);
        }
        if (this.tokenName != null) {
            advancedApi20.setTokenName(tokenName);
        }
        if (this.tokenExtractMethod != null) {
            advancedApi20.setTokenExtractMethod(tokenExtractMethod);
        }
        GenericOAuth20ProfileDefinition profileDefinition = new GenericOAuth20ProfileDefinition();
        profileDefinition.setFirstNodePath(this.profilePath);
        profileDefinition.setProfileVerb(this.profileVerb);
        profileDefinition.setProfileUrl(this.profileUrl);
        if (this.profileId != null) {
            profileDefinition.setProfileId(this.profileId);
        } else {
            profileDefinition.setProfileId("id");
        }
        configuration.setProfileDefinition(profileDefinition);
        configuration.setScope(scopeValue);
        configuration.setWithState(withState);
        configuration.setTokenAsHeader(tokenAsHeader);
        AdvancedOauth2ProfileCreator advancedOauth2ProfileCreator = new AdvancedOauth2ProfileCreator(configuration, this);
        if (this.tokenParamName != null) {
            advancedOauth2ProfileCreator.setTokenParamName(tokenParamName);
        }
        defaultProfileCreator(advancedOauth2ProfileCreator);
        super.clientInit();
    }

}
