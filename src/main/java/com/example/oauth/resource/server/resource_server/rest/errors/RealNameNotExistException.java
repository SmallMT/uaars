package com.example.oauth.resource.server.resource_server.rest.errors;

public class RealNameNotExistException extends BadRequestAlertException {

    public RealNameNotExistException(){
        super("对应的实名认证信息不存在","realName","login");

    }
}
