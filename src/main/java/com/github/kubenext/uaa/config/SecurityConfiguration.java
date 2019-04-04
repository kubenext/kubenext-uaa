package com.github.kubenext.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lishangjin
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/test").authenticated()
            .anyRequest()
                .authenticated()
            .and().formLogin()
                .loginPage("/rlogin")
                .loginProcessingUrl("/myauth")
                .failureForwardUrl("/rlogin")
                .usernameParameter("user")
                .passwordParameter("pass")
                .permitAll()
                .defaultSuccessUrl("/toSuc", false)
            .and().httpBasic()
            .and().csrf().disable();

    }

}
