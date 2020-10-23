package com.demtem.birthday_messaging.services;

import java.util.Map;

public class OAuth2UserInfo {

    private Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() { return attributes; }

    public String getId() { return (String) attributes.get("sub"); }

    public String getFirstName() {
        return (String) attributes.get("given_name");
    }

    public String getLastName(){
        return (String) attributes.get("family_name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

}