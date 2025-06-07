package com.app.moviebooking.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String email) {
        super("Email '" + email + "' already taken");
    }
}