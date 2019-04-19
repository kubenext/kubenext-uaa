package com.github.kubenext.uaa.validation.constraints;

import com.github.kubenext.uaa.validation.validator.AuthorityNonValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AuthorityNonValidator.class)
public @interface AuthorityNon {

    String message() default "Authority is not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
