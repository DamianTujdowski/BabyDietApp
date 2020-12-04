package com.dietator.diet.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
class ApiException {

    private HttpStatus status;
    private String exception;
    private String message;
    private String path;

    ApiException(HttpStatus status, String exception, String message, String path) {
        this.status = status;
        this.exception = exception;
        this.message = message;
        this.path = path.substring(path.indexOf("/"));
    }
}
