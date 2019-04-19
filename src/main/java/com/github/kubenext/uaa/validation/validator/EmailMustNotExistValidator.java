package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.validation.constraints.EmailMustNotExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author shangjin.li
 */
public class EmailMustNotExistValidator implements ConstraintValidator<EmailMustNotExist, String> {

    private final UserRepository userRepository;

    public EmailMustNotExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userRepository.findOneByEmailIgnoreCase(value).isPresent()) {
            return false;
        }
        return true;
    }
}
