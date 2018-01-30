package com.example.oauth.resource.server.resource_server.service.dto;

import com.example.oauth.resource.server.resource_server.service.filter.BooleanFilter;
import com.example.oauth.resource.server.resource_server.service.filter.StringFilter;

import java.io.Serializable;

public class UserCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private StringFilter login;

    private StringFilter identity;

    private StringFilter wechat;

    private BooleanFilter verified;

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getIdentity() {
        return identity;
    }

    public void setIdentity(StringFilter identity) {
        this.identity = identity;
    }

    public BooleanFilter getVerified() {
        return verified;
    }

    public void setVerified(BooleanFilter verified) {
        this.verified = verified;
    }

    public StringFilter getWechat() {
        return wechat;
    }

    public void setWechat(StringFilter wechat) {
        this.wechat = wechat;
    }
}
