package com.example.oauth.resource.server.resource_server.rest.errors;

public class CanntFindUserException extends BadRequestAlertException {

    public CanntFindUserException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }
}
