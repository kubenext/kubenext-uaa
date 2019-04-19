package com.github.kubenext.uaa.validation.constraints;

import com.github.kubenext.uaa.validation.validator.LoginDuplicateValidator;

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
@Constraint(validatedBy = LoginDuplicateValidator.class)
public @interface LoginDuplicate {

    String message() default "User login is duplicate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
