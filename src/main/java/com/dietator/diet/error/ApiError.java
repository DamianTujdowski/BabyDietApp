package com.dietator.diet.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {

    private HttpStatus status;
    private String message;
    private String error;
    private String path;

    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public ApiError(HttpStatus status, String message, String error, String path) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
    }
}
