package com.example.oauth.resource.server.resource_server.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;


/**
 * 异常
 */
public class VerificationCodeUpperLimitException extends AbstractThrowableProblem {

    public VerificationCodeUpperLimitException(String detail) {
        super(ErrorConstants.VERFICATIONCODE_INVALID_TYPE, "请求验证码错误", Status.BAD_REQUEST, detail);
    }
}
