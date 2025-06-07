package com.example.moviebooking.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String email) {
        super("Email '" + email + "' already taken");
    }
}