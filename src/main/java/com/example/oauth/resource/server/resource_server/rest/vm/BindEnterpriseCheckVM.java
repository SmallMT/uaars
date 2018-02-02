package com.example.oauth.resource.server.resource_server.rest.vm;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class BindEnterpriseCheckVM {

    @NotBlank(message = "状态不能为空")
    private String state;

    @NotNull(message = "id不能为空")
    private Integer id;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
