package com.github.kubenext.uaa.validation.constraints;

import com.github.kubenext.uaa.validation.validator.EmailMustNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailMustNotExistValidator.class)
public @interface EmailMustNotExist {

    String message() default "{validation.user.email.must.not.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
