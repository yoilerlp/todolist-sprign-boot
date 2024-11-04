package com.kiraly.todolist.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor

public class ApiResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
}
