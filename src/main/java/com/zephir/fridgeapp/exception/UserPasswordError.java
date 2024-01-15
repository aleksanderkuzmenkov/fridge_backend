package com.zephir.fridgeapp.exception;

public class UserPasswordError extends RuntimeException {
    public UserPasswordError(String message) {
        super(message);
    }
}
