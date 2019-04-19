package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.validation.constraints.UserIdExist;
import com.github.kubenext.uaa.validation.constraints.UserIdNon;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author shangjin.li
 */
public class UserIdNonValidator implements ConstraintValidator<UserIdNon, Long> {

    private final UserRepository userRepository;

    public UserIdNonValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (userRepository.findById(value).isPresent()) {
            return false;
        }
        return true;
    }
}
