package com.example.oauth.resource.server.resource_server.service.dto;

import com.example.oauth.resource.server.resource_server.service.filter.StringFilter;

import java.io.Serializable;

public class BindEnterpriseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private StringFilter login;

    private StringFilter creditCode;

    private StringFilter state;

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(StringFilter creditCode) {
        this.creditCode = creditCode;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }
}

