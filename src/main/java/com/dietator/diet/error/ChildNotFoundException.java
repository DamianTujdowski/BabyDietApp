package com.dietator.diet.error;

public class ChildNotFoundException extends RuntimeException {
    public ChildNotFoundException(long id) {
        super("Child with id: " + id  + " does not exist");
    }
}
