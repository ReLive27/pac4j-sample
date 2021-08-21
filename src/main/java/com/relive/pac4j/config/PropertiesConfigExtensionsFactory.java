package com.relive.pac4j.config;

import com.relive.pac4j.builder.Cas2Builder;
import com.relive.pac4j.builder.OAuth2Builder;
import org.pac4j.config.client.PropertiesConfigFactory;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.authorization.generator.FromAttributesAuthorizationGenerator;
import org.pac4j.core.client.Client;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.CommonHelper;

import java.util.List;
import java.util.Map;

/**
 * @Author ReLive
 * @Date 2021/6/1-11:02
 */
public class PropertiesConfigExtensionsFactory extends PropertiesConfigFactory {

    private AuthorizationGenerator authorizationGenerator = new FromAttributesAuthorizationGenerator();

    public PropertiesConfigExtensionsFactory(String callbackUrl, Map<String, String> properties) {
        super(callbackUrl, properties);
    }

    @Override
    public Config build(Object... parameters) {
        Config config = super.build(parameters);
        List<Client> clients = config.getClients().getClients();
        if (this.hasOAuth2Clients()) {
            OAuth2Builder oAuth2Builder = new OAuth2Builder(this.properties);
            oAuth2Builder.tryCreateQQClient(clients);
            oAuth2Builder.tryCreateWechatClient(clients);
            oAuth2Builder.tryCreateWeiboClient(clients);
            oAuth2Builder.tryCreateAdvancedOAuth2Client(clients);
        }
        if (this.hasCas2Clients()) {
            Cas2Builder cas2Builder = new Cas2Builder(this.properties);
            cas2Builder.tryCreateCasClient(clients);
        }
        config.getClients().setAuthorizationGenerator(authorizationGenerator);
        return config;
    }

    protected boolean hasOAuth2Clients() {
        if (CommonHelper.isNotBlank(this.getProperty("qq.id")) && CommonHelper.isNotBlank(this.getProperty("qq.secret"))) {
            return true;
        } else if (CommonHelper.isNotBlank(this.getProperty("wechat.id")) && CommonHelper.isNotBlank(this.getProperty("wechat.secret"))) {
            return true;
        } else if (CommonHelper.isNotBlank(this.getProperty("weibo.id")) && CommonHelper.isNotBlank(this.getProperty("weibo.secret"))) {
            return true;
        } else if (CommonHelper.isNotBlank(this.getProperty("advancedOAuth.id")) && CommonHelper.isNotBlank(this.getProperty("advancedOAuth.secret"))) {
            return true;
        }
        return false;
    }

    protected boolean hasCas2Clients() {
        if (CommonHelper.isNotBlank(this.getProperty("cas2.loginUrl"))) {
            return true;
        }
        return false;
    }

    public void setAuthorizationGenerator(AuthorizationGenerator authorizationGenerator) {
        this.authorizationGenerator = authorizationGenerator;
    }
}
