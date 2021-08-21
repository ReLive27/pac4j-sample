package com.relive.pac4j.builder;

import com.relive.utils.SpringContextUtils;
import org.jasig.cas.client.validation.TicketValidator;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.config.builder.AbstractBuilder;
import org.pac4j.core.client.Client;
import org.pac4j.core.util.CommonHelper;

import java.util.List;
import java.util.Map;

/**
 * @Author ReLive
 * @Date 2021/5/8-13:33
 */
public class Cas2Builder extends AbstractBuilder {
    public Cas2Builder(Map<String, String> properties) {
        super(properties);
    }

    public void tryCreateCasClient(List<Client> clients) {
        String loginUrl = this.getProperty("cas2.loginUrl");
        String protocol = this.getProperty("cas2.protocol");
        String ticketValidator = this.getProperty("cas2.ticketValidator");
        if (CommonHelper.isNotBlank(loginUrl)) {
            CasConfiguration configuration = new CasConfiguration();
            CasClient casClient = new CasClient(configuration);
            configuration.setLoginUrl(loginUrl);
            if (CommonHelper.isNotBlank(protocol)) {
                configuration.setProtocol(CasProtocol.valueOf(protocol));
            }
            if (CommonHelper.isNotBlank(ticketValidator)) {
                configuration.setDefaultTicketValidator((TicketValidator) SpringContextUtils.getBean(ticketValidator));
            }
            casClient.setName(casClient.getName() + "2");
            clients.add(casClient);
        }

    }
}
