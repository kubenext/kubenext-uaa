package com.github.kubenext.uaa.security;


import org.springframework.security.core.AuthenticationException;

/**
 * @author shangjin.li
 */
public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }

}
