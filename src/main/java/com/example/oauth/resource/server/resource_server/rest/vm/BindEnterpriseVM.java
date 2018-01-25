package com.example.oauth.resource.server.resource_server.rest.vm;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class BindEnterpriseVM {

    private Integer id;


    @NotNull(message = "登录用户名不能为空")
    @NotBlank(message = "登录用户名不能为空")
    private String login;

    @NotNull(message = "企业名称不能为空")
    @NotBlank(message = "企业名称不能为空")
    private String enterpriseName;

    @NotNull(message = "社会信用统一代码不能为空")
    @NotBlank(message = "社会信用统一代码不能为空")
    private String creditCode;

    @NotNull(message = "状态不能为空")
    @NotBlank(message = "状态不能为空")
    private String state;

    @NotNull(message = "法人姓名不能为空")
    @NotBlank(message = "法人姓名不能为空")
    private String legalPersonName;

    @NotNull(message = "法人手机号码不能为空")
    @NotBlank(message = "法人手机号码不能为空")
    private String legalPersonPhone;

    @NotNull(message = "法人社会信用代码不能为空")
    @NotBlank(message = "法人社会信用代码不能为空")
    private String enterpriseAddress;

    @NotNull(message = "法人身份证号码不能为空")
    @NotBlank(message = "法人身份证号码不能为空")
    private String legalPersonID;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonPhone() {
        return legalPersonPhone;
    }

    public void setLegalPersonPhone(String legalPersonPhone) {
        this.legalPersonPhone = legalPersonPhone;
    }

    public String getEnterpriseAddress() {
        return enterpriseAddress;
    }

    public void setEnterpriseAddress(String enterpriseAddress) {
        this.enterpriseAddress = enterpriseAddress;
    }

    public String getLegalPersonID() {
        return legalPersonID;
    }

    public void setLegalPersonID(String legalPersonID) {
        this.legalPersonID = legalPersonID;
    }
}
