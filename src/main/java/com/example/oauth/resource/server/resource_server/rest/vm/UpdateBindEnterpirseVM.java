package com.example.oauth.resource.server.resource_server.rest.vm;


import javax.validation.constraints.NotNull;

public class UpdateBindEnterpirseVM extends BindEnterpriseVM {

    @NotNull
    private Integer id;


    public Integer getId() {
        return id;
    }

    public void setId( Integer id) {
        this.id = id;
    }
}
