package com.example.oauth.resource.server.resource_server.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class RealNameExistException extends AbstractThrowableProblem {
    public RealNameExistException() {
        super(ErrorConstants.REALNAME_EXIST_TYPE, "实名认证信息已存在", Status.BAD_REQUEST, "实名认证信息已经存在，不能重复添加");
    }
}
