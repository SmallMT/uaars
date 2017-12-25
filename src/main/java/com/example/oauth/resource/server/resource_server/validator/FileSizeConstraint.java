package com.example.oauth.resource.server.resource_server.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileSizeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSizeConstraint {
    String message() default "文件大小不超过1M";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
