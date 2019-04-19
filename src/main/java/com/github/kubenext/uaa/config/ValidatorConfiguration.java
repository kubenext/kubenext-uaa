package com.github.kubenext.uaa.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * @author shangjin.li
 */
@Configuration
public class ValidatorConfiguration {

    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }


}
