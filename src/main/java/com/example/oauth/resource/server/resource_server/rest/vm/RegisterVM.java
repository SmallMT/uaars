package com.example.oauth.resource.server.resource_server.rest.vm;

import javax.annotation.RegEx;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

public class RegisterVM {
    @NotNull(message = "电话号码不为空")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$",message = "请输入正确的手机号码格式")
    private String phone;

    @NotNull(message = "密码不能为空")
    @Size(min = 4, max = 100,message = "长度在4到100之间")
    private String password;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @Size(min = 4,max = 100,message = "长度在4到100之间")
    private String login;

    /**
     * 用户类型（ROLE_ADMIN ROLE_USER）
     */
    private Set<String> authorities;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
