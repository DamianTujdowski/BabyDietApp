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
        ApiException apiException = createEntityNotFoundException(exception, request);
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

    private ApiException createEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        return new ApiException(HttpStatus.NOT_FOUND,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleMinusPageNumber(IllegalArgumentException exception, WebRequest request) {
        ApiException apiException = createMinusPageNumberException(exception, request);
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

    private ApiException createMinusPageNumberException(IllegalArgumentException exception, WebRequest request) {
        return new ApiException(HttpStatus.BAD_REQUEST,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                request.getDescription(false));
    }
}
