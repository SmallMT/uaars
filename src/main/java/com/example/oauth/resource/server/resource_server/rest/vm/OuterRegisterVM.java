package com.example.oauth.resource.server.resource_server.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class OuterRegisterVM extends RegisterVM {

    @NotEmpty(message = "短信验证码不能为空")
    @NotNull(message = "短信验证码不能为空")
    private String smsCode;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
