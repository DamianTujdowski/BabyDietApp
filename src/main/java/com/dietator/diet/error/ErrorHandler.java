package com.dietator.diet.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage(), exception.getClass().getSimpleName(), request.getContextPath());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
//        return handleExceptionInternal(exception, error, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request);
    }
}
