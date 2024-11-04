package com.kiraly.todolist.exceptions;

public class UserAllReadyExistsException extends RuntimeException {
    public UserAllReadyExistsException(String message) {
        super(message);
    }
}
