package com.revature.app.utils.custom_exceptions;

public class InvalidCredentialException extends RuntimeException {

    public InvalidCredentialException() {
        super("Invalid credentials");
    }
}
