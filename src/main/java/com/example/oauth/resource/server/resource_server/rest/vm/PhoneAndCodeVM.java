package com.example.oauth.resource.server.resource_server.rest.vm;

import javax.validation.constraints.NotNull;

/**
 *电话号码和验证码
 */
public class PhoneAndCodeVM {

    @NotNull(message = "电话号码不能为空")
    private String phone;

    @NotNull(message = "短信验证码不能为空")
    private String code;

    @NotNull(message = "登录用户名不能为空")
    private String login;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
