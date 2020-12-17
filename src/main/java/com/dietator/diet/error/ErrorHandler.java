package com.dietator.diet.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        ApiException apiException = createEntityNotFoundException(exception, request);
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

    @ExceptionHandler(ParentEntityNotFoundException.class)
    public ResponseEntity<Object> handleParentEntityNotFoundException(ParentEntityNotFoundException exception, WebRequest request) {
        ApiException apiException = createEntityNotFoundException(exception, request);
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

    private ApiException createEntityNotFoundException(RuntimeException exception, WebRequest request) {
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiException apiException = createValidationException(ex, request);
        return new ResponseEntity<>(apiException, headers, apiException.getStatus());
    }

    private ApiException createValidationException(MethodArgumentNotValidException exception, WebRequest request) {
        return new ApiException(HttpStatus.BAD_REQUEST,
                exception.getClass().getSimpleName(),
                getErrorsMessages(exception),
                request.getDescription(false));
    }

    private String getErrorsMessages(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::getFieldErrorMessage)
                .collect(Collectors.joining("\n"));
    }

    private String getFieldErrorMessage(ObjectError error) {
        String fieldName = ((FieldError) error).getField();
        String message = error.getDefaultMessage();
        return fieldName + ": " + message;
    }


}
