package com.relive.pac4j.client.oauth;

import com.github.scribejava.core.model.Verb;
import org.pac4j.core.exception.TechnicalException;

public class ProfileVerbFactory {

    public static Verb getVerb(String method) {
        switch (method) {
            case "get":
                return Verb.GET;
            case "post":
                return Verb.POST;
            case "delete":
                return Verb.DELETE;
            case "put":
                return Verb.PUT;
            default:
                throw new TechnicalException("Unsupported request method: " + method);
        }
    }
}
