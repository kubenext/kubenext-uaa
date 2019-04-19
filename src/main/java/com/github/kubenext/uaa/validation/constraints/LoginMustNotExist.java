package com.github.kubenext.uaa.validation.constraints;

import com.github.kubenext.uaa.validation.validator.LoginMustNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shangjin.li
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginMustNotExistValidator.class)
public @interface LoginMustNotExist {

    String message() default "{validation.user.login.must.not.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
