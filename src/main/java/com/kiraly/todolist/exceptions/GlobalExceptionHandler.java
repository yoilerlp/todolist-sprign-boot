package com.kiraly.todolist.exceptions;

import com.kiraly.todolist.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({RequestBasicException.class})
    public ResponseEntity<ErrorResponse> handleBasicException(RequestBasicException ex) {
            var responseObject = ErrorResponse.builder()
                    .message(ex.getMessage())
                    .statusCode(ex.getStatusCode())
                    .error(ex.getMessage())
                    .build();
            return ResponseEntity.status(ex.getStatusCode()).body(responseObject);

    }



    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleStudentNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .message(exception.getMessage())
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleInvalidBodyException( HttpMessageNotReadableException exception)
    {
        System.out.println(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid request")
                        .error("Invalid request Body or Format")
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        var errorBody = ErrorResponse.builder().
                statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Invalid request")
                .message(errors)
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

}
