package com.relive.pac4j.client.oauth;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth2.clientauthentication.ClientAuthentication;
import com.github.scribejava.core.oauth2.clientauthentication.HttpBasicAuthenticationScheme;
import com.github.scribejava.core.oauth2.clientauthentication.RequestBodyAuthenticationScheme;
import lombok.Setter;
import org.pac4j.core.exception.TechnicalException;

/**
 * 自定义 OAuth2 api
 *
 * @Author ReLive
 * @Date 2021/4/2-17:10
 */
@Setter
public class AdvancedApi20 extends DefaultApi20 {

    public static final String BASIC_AUTH_AUTHENTICATION_METHOD = "basicAuth";
    public static final String REQUEST_BODY_AUTHENTICATION_METHOD = "requestBody";
    public static final String TOKEN_EXTRACT_JSON_METHOD = "json";
    public static final String TOKEN_EXTRACT_PARAM_METHOD = "param";

    protected final String authUrl;
    protected final String tokenUrl;
    protected Verb accessTokenVerb;
    protected String clientAuthenticationMethod;
    protected String tokenName;
    protected String tokenExtractMethod;

    protected AdvancedApi20(String authUrl, String tokenUrl) {
        this.authUrl = authUrl;
        this.tokenUrl = tokenUrl;
        this.accessTokenVerb = Verb.POST;
        this.clientAuthenticationMethod = REQUEST_BODY_AUTHENTICATION_METHOD;
        this.tokenExtractMethod = TOKEN_EXTRACT_JSON_METHOD;
        this.tokenName = OAuthConstants.ACCESS_TOKEN;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return tokenUrl;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return authUrl;
    }

    public Verb getAccessTokenVerb() {
        return accessTokenVerb;
    }

    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        if (TOKEN_EXTRACT_JSON_METHOD.equalsIgnoreCase(tokenExtractMethod)) {
            AdvancedOAuth2JsonExtractor instance = AdvancedOAuth2JsonExtractor.instance();
            instance.setTokenName(tokenName);
            return instance;
        } else if (TOKEN_EXTRACT_PARAM_METHOD.equalsIgnoreCase(tokenExtractMethod)) {
            return OAuth2AccessTokenExtractor.instance();
        } else {
            throw new TechnicalException("Unsupported token extract method: " + clientAuthenticationMethod);
        }
    }

    @Override
    public ClientAuthentication getClientAuthentication() {
        if (BASIC_AUTH_AUTHENTICATION_METHOD.equalsIgnoreCase(clientAuthenticationMethod)) {
            return HttpBasicAuthenticationScheme.instance();
        } else if (REQUEST_BODY_AUTHENTICATION_METHOD.equalsIgnoreCase(clientAuthenticationMethod)) {
            return RequestBodyAuthenticationScheme.instance();
        } else {
            throw new TechnicalException("Unsupported client authentication method: " + clientAuthenticationMethod);
        }
    }
}
