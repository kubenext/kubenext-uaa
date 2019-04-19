package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.validation.constraints.LoginMustNotExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author shangjin.li
 */
public class LoginMustNotExistValidator implements ConstraintValidator<LoginMustNotExist, String> {

    private final UserRepository userRepository;

    public LoginMustNotExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (userRepository.findOneByLogin(value).isPresent()) {
            return false;
        }
        return true;
    }
}
