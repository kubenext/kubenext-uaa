package com.github.kubenext.uaa.validation.constraints;

import com.github.kubenext.uaa.validation.validator.EmailDuplicateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailDuplicateValidator.class)
public @interface EmailDuplicate {

    String message() default "User email is duplicate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
