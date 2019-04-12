package com.github.kubenext.uaa.web.rest.errors;

/**
 * @author shangjin.li
 */
public class LoginAlreadyUsedException extends BadRequestAlertException {
    public LoginAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login name already used!", "userManagement", "userexists");
    }
}
