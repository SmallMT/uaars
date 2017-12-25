package com.example.oauth.resource.server.resource_server.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSizeConstraint,MultipartFile> {
    @Override
    public void initialize(FileSizeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value.getSize()>=1048576?false:true;
    }
}
