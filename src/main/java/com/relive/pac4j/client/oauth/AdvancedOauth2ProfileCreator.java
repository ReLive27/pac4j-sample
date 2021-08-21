package com.relive.pac4j.client.oauth;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.creator.OAuth20ProfileCreator;

/**
 * @Author ReLive
 * @Date 2021/4/20-15:15
 */
public class AdvancedOauth2ProfileCreator extends OAuth20ProfileCreator {
    private String tokenParamName = HttpConstants.AUTHORIZATION_HEADER;

    public AdvancedOauth2ProfileCreator(OAuth20Configuration configuration, IndirectClient client) {
        super(configuration, client);
    }

    @Override
    protected void signRequest(final OAuth20Service service, final OAuth2AccessToken accessToken,
                               final OAuthRequest request) {
        service.signRequest(accessToken, request);
        if (this.configuration.isTokenAsHeader()) {
            request.addHeader(HttpConstants.AUTHORIZATION_HEADER, HttpConstants.BEARER_HEADER_PREFIX + accessToken.getAccessToken());
        }
        //TODO 待更改
        if (Verb.POST.equals(request.getVerb())) {
            request.addParameter(tokenParamName, accessToken.getAccessToken());
        }
    }

    public void setTokenParamName(String tokenParamName) {
        this.tokenParamName = tokenParamName;
    }
}
