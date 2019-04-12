package com.github.kubenext.uaa.web.rest.errors;

import java.io.Serializable;

/**
 * @author shangjin.li
 */
public class FieldErrorVM implements Serializable {

    private final String objectName;

    private final String field;

    private final String message;

    public FieldErrorVM(String objectName, String field, String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
