package com.github.kubenext.uaa.validation.constraints;

import com.github.kubenext.uaa.validation.validator.UserIdMustExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户ID是否存在
 *
 * @author shangjin.li
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIdMustExistValidator.class)
public @interface UserIdMustExist {

    String message() default "{validation.user.id.must.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
