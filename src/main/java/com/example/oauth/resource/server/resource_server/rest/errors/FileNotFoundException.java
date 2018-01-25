package com.example.oauth.resource.server.resource_server.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class FileNotFoundException extends AbstractThrowableProblem {

    public FileNotFoundException() {
        super(ErrorConstants.DEFAULT_TYPE,"文件不存在", Status.NOT_FOUND);
    }
}
