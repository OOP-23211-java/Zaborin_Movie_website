package com.example.moviebooking.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String username) {
        super("Login '" + username + "' already taken");
    }
}