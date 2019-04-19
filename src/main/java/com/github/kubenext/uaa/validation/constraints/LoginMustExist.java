package com.github.kubenext.uaa.validation.constraints;

import com.github.kubenext.uaa.validation.validator.LoginMustExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginMustExistValidator.class)
public @interface LoginMustExist {

    String message() default "{validation.user.login.must.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
