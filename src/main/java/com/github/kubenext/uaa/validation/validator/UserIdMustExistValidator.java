package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.validation.constraints.UserIdMustExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author shangjin.li
 */
public class UserIdMustExistValidator implements ConstraintValidator<UserIdMustExist, Long> {

    private final UserRepository userRepository;

    public UserIdMustExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (!userRepository.findById(value).isPresent()) {
            return false;
        }
        return true;
    }
}
