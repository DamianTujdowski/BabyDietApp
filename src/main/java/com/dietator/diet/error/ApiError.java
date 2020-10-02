package com.dietator.diet.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {

    private HttpStatus status;
    private String error;
    private String message;
    private String path;

    public ApiError(HttpStatus status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path.substring(path.indexOf("/"));
    }
}
