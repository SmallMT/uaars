package com.example.oauth.resource.server.resource_server.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UpdatePhoneVM {

    @NotNull(message = "用户名不能为空")
    private String login;

    @NotNull(message = "电话号码不为空")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$",message = "请输入正确的手机号码格式")

    private String phone;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
