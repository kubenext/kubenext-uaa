package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.validation.constraints.EmailDuplicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author shangjin.li
 */
public class EmailDuplicateValidator implements ConstraintValidator<EmailDuplicate, String> {

    private final UserRepository userRepository;

    public EmailDuplicateValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userRepository.findOneByEmailIgnoreCase(value).isPresent()) {
            return false;
        } else {
            return true;
        }
    }
}
