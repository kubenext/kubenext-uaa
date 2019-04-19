package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.validation.constraints.UserIdExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author shangjin.li
 */
public class UserIdExistValidator implements ConstraintValidator<UserIdExist, Long> {

    private final UserRepository userRepository;

    public UserIdExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (userRepository.findById(value).isPresent()) {
            return true;
        }
        return false;
    }
}
