package com.relive.pac4j.client.oauth;

import org.pac4j.oauth.profile.OAuth20Profile;

/**
 * @Author ReLive
 * @Date 2021/4/2-17:31
 */
@Deprecated
public class CustomProfile extends OAuth20Profile {
    //根据自己实际获取字段增加
    public String getAccountNumber() {
        return (String) this.getAttribute(CustomProfileDefinition.ACCOUNT_NUMBER);
    }

    public String getMail() {
        return (String) this.getAttribute(CustomProfileDefinition.MAIL);
    }

    public String getPhoneNumber() {
        return (String) this.getAttribute(CustomProfileDefinition.PHONE_NUMBER);
    }

    public String getUserName() {
        return (String) this.getAttribute(CustomProfileDefinition.USERNAME);
    }
}
