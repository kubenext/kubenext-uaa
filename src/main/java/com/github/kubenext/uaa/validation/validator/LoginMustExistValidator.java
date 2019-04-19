package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.validation.constraints.LoginMustExist;
import com.github.kubenext.uaa.validation.constraints.LoginMustNotExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author shangjin.li
 */
public class LoginMustExistValidator implements ConstraintValidator<LoginMustExist, String> {

    private final UserRepository userRepository;

    public LoginMustExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (!userRepository.findOneByLogin(value).isPresent()) {
            return false;
        }
        return true;
    }
}
