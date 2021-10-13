package com.oshovskii.springSecurityApp.exceptions_handling;

public class IncorrectFieldException extends RuntimeException {
    public IncorrectFieldException(String message) {
        super(message);
    }
}
