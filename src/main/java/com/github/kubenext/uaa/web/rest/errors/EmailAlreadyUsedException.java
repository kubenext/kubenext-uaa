package com.github.kubenext.uaa.web.rest.errors;

import java.net.URI;

/**
 * @author shangjin.li
 */
public class EmailAlreadyUsedException extends BadRequestAlertException {
    public EmailAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email is already in use!", "userManagement", "emailexists");
    }
}
