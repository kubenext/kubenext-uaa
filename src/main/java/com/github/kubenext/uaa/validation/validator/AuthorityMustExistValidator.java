package com.github.kubenext.uaa.validation.validator;

import com.github.kubenext.uaa.domain.Authority;
import com.github.kubenext.uaa.repository.AuthorityRepository;
import com.github.kubenext.uaa.validation.constraints.AuthorityMustExist;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shangjin.li
 */
public class AuthorityMustExistValidator implements ConstraintValidator<AuthorityMustExist, String[]> {

    private final AuthorityRepository authorityRepository;

    public AuthorityMustExistValidator(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        Set<Authority> authorities = Arrays.stream(value)
                .map(authority -> authorityRepository.findById(authority))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(authorities)) {
            return false;
        }
        return true;
    }
}
