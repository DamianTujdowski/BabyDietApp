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
        ApiError error = createApiError(exception, request);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }

    private ApiError createApiError(EntityNotFoundException exception, WebRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                request.getDescription(false));
    }
}
