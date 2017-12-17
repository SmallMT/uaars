package com.example.oauth.resource.server.resource_server.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * 验证码验证失败异常
 */
public class VerficationCodeInvalidException extends AbstractThrowableProblem {

    public VerficationCodeInvalidException(String detail) {
        super(ErrorConstants.VERFICATIONCODE_UPPER_LIMIT_TYPE, "校验验证码错误", Status.BAD_REQUEST, detail);
    }
}
