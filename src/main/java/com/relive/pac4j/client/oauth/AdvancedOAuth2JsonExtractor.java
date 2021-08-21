package com.relive.pac4j.client.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.utils.Preconditions;
import org.pac4j.oauth.profile.JsonHelper;

import java.io.IOException;

/**
 * @Author ReLive
 * @Date 2021/4/20-14:03
 */
public class AdvancedOAuth2JsonExtractor extends OAuth2AccessTokenJsonExtractor {
    protected String tokenName;

    protected AdvancedOAuth2JsonExtractor() {
    }

    public static AdvancedOAuth2JsonExtractor instance() {
        return AdvancedOAuth2JsonExtractor.InstanceHolder.INSTANCE;
    }

    public OAuth2AccessToken extract(Response response) throws IOException {
        String body = response.getBody();
        Preconditions.checkEmptyString(body, "Response body is incorrect. Can't extract a token from an empty string");
        if (response.getCode() != 200) {
            this.generateError(body);
        }

        return this.createToken(body);
    }

    private OAuth2AccessToken createToken(String rawResponse) throws IOException {
        JsonNode response = OBJECT_MAPPER.readTree(rawResponse);
        JsonNode expiresInNode = response.get("expires_in");
        JsonNode refreshToken = response.get("refresh_token");
        JsonNode scope = response.get("scope");
        JsonNode tokenType = response.get("token_type");
        return this.createToken(extractRequiredParameter(response, tokenName, rawResponse).asText(), tokenType == null ? null : tokenType.asText(), expiresInNode == null ? null : expiresInNode.asInt(), refreshToken == null ? null : refreshToken.asText(), scope == null ? null : scope.asText(), response, rawResponse);
    }

    protected static JsonNode extractRequiredParameter(JsonNode errorNode, String parameterName, String rawResponse) throws OAuthException {
        JsonNode node = errorNode;
        if (errorNode != null && parameterName != null) {
            String[] paramNames = parameterName.split("\\.");
            int length = paramNames.length;
            for (int var = 0; var < length; var++) {
                String nodeName = paramNames[var];
                if (node != null) {
                    if (nodeName.matches("\\d+")) {
                        node.get(Integer.parseInt(nodeName));
                    } else {
                        node = node.get(nodeName);
                    }
                }
            }
        }
        if (node == null) {
            throw new OAuthException("Response body is incorrect. Can't extract a '" + parameterName + "' from this: '" + rawResponse + "'", (Exception) null);
        } else {
            return node;
        }
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    private static class InstanceHolder {
        private static final AdvancedOAuth2JsonExtractor INSTANCE = new AdvancedOAuth2JsonExtractor();

        private InstanceHolder() {
        }
    }

}
