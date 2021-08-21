package com.relive.pac4j.builder;

import com.relive.pac4j.client.oauth.AdvancedOAuth2Client;
import com.relive.pac4j.client.oauth.ProfileVerbFactory;
import org.pac4j.config.builder.AbstractBuilder;
import org.pac4j.core.client.Client;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.oauth.client.QQClient;
import org.pac4j.oauth.client.WechatClient;
import org.pac4j.oauth.client.WeiboClient;

import java.util.List;
import java.util.Map;

/**
 * @Author ReLive
 * @Date 2021/5/7-19:41
 */
public class OAuth2Builder extends AbstractBuilder {
    public OAuth2Builder(Map<String, String> properties) {
        super(properties);
    }

    public void tryCreateQQClient(List<Client> clients) {
        String id = this.getProperty("qq.id");
        String secret = this.getProperty("qq.secret");
        if (CommonHelper.isNotBlank(id) && CommonHelper.isNotBlank(secret)) {
            QQClient client = new QQClient(id, secret);
            clients.add(client);
        }
    }

    public void tryCreateWechatClient(List<Client> clients) {
        String id = this.getProperty("wechat.id");
        String secret = this.getProperty("wechat.secret");
        if (CommonHelper.isNotBlank(id) && CommonHelper.isNotBlank(secret)) {
            WechatClient client = new WechatClient(id, secret);
            clients.add(client);
        }
    }

    public void tryCreateWeiboClient(List<Client> clients) {
        String id = this.getProperty("weibo.id");
        String secret = this.getProperty("weibo.secret");
        if (CommonHelper.isNotBlank(id) && CommonHelper.isNotBlank(secret)) {
            WeiboClient client = new WeiboClient(id, secret);
            clients.add(client);
        }
    }

    public void tryCreateAdvancedOAuth2Client(List<Client> clients) {
        String id = this.getProperty("advancedOAuth.id");
        String secret = this.getProperty("advancedOAuth.secret");
        if (CommonHelper.isNotBlank(id) && CommonHelper.isNotBlank(secret)) {
            AdvancedOAuth2Client client = new AdvancedOAuth2Client(id, secret);
            client.setAuthUrl(this.getProperty("advancedOAuth.authUrl"));
            client.setTokenUrl(this.getProperty("advancedOAuth.tokenUrl"));
            client.setProfileUrl(this.getProperty("advancedOAuth.profileUrl"));
            client.setProfileId(this.getProperty("advancedOAuth.profileId"));
            client.setProfilePath(this.getProperty("advancedOAuth.profilePath"));
            if (this.containsProperty("advancedOAuth.scope", 0)) {
                client.setScopeValue(this.getProperty("advancedOAuth.scope"));
            }
            if (this.containsProperty("advancedOAuth.clientAuthenticationMethod", 0)) {
                client.setClientAuthenticationMethod(this.getProperty("advancedOAuth.clientAuthenticationMethod"));
            }
            if (this.containsProperty("advancedOAuth.tokenName", 0)) {
                client.setTokenName(this.getProperty("advancedOAuth.tokenName"));
            }
            if (this.containsProperty("advancedOAuth.tokenExtractMethod", 0)) {
                client.setTokenExtractMethod(this.getProperty("advancedOAuth.tokenExtractMethod"));
            }
            if (this.containsProperty("advancedOAuth.profileRequestMethod", 0)) {
                client.setProfileVerb(ProfileVerbFactory.getVerb(this.getProperty("advancedOAuth.profileRequestMethod")));
            }
            if (this.containsProperty("advancedOAuth.tokenAsHeader", 0)) {
                client.setTokenAsHeader(this.getPropertyAsBoolean("advancedOAuth.tokenAsHeader", 0));
            }
            if (this.containsProperty("advancedOAuth.withState", 0)) {
                client.setWithState(this.getPropertyAsBoolean("advancedOAuth.withState", 0));
            }
            if (this.containsProperty("advancedOAuth.tokenParamName", 0)) {
                client.setTokenParamName(this.getProperty("advancedOAuth.tokenParamName"));
            }
            clients.add(client);
        }
    }
}
