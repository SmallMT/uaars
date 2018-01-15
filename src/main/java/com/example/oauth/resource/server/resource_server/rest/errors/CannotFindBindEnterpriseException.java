package com.example.oauth.resource.server.resource_server.rest.errors;

public class CannotFindBindEnterpriseException extends BadRequestAlertException {

    public CannotFindBindEnterpriseException() {
        super("没有找到相应企业绑定信息", "bindEnterprise", "id");
    }
}
