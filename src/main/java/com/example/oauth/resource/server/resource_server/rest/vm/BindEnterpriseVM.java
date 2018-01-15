package com.example.oauth.resource.server.resource_server.rest.vm;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class BindEnterpriseVM {

    private Integer id;


    @NotNull(message = "登录用户名不能为空")
    private String login;

    @NotNull(message = "企业名称不能为空")
    private String enterpriseName;

    @NotNull(message = "社会信用统一代码不能为空")
    private String creditCode;

    private String businessLicense;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }
}
