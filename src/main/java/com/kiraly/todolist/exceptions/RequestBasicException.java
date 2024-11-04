package com.kiraly.todolist.exceptions;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class RequestBasicException extends RuntimeException {
    private Integer statusCode;
    private String message;

    public RequestBasicException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;

    }
}
