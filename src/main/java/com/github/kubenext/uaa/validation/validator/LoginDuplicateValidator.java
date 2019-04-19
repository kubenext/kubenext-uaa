package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.validation.constraints.LoginDuplicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author shangjin.li
 */
public class LoginDuplicateValidator implements ConstraintValidator<LoginDuplicate, String> {

    private final UserRepository userRepository;

    public LoginDuplicateValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userRepository.findOneByLogin(value).isPresent()) {
            return false;
        } else {
            return true;
        }
    }
}
