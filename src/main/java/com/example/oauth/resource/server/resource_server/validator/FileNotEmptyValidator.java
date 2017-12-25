package com.example.oauth.resource.server.resource_server.validator;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class FileNotEmptyValidator implements ConstraintValidator<FileNotEmptyConstraint,MultipartFile> {
    @Override
    public void initialize(FileNotEmptyConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return !value.isEmpty();
    }


}
