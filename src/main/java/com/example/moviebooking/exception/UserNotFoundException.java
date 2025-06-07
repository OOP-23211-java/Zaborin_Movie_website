package com.example.moviebooking.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User with name '" + username + "' not found");
    }
}