package com.example.oauth.resource.server.resource_server.rest.errors;

public class CreditCodeExistException extends BadRequestAlertException {
    public CreditCodeExistException(){
        super("包含该统一信用代码的信息已经存在","bindEnterprise","creditCodeExist");
    }
}
