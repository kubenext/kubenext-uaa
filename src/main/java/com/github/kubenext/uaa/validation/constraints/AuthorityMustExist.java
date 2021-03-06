package com.github.kubenext.uaa.validation.constraints;

import com.github.kubenext.uaa.validation.validator.AuthorityMustExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AuthorityMustExistValidator.class)
public @interface AuthorityMustExist {

    String message() default "{validation.authority.must.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
