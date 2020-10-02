package com.dietator.diet.error;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class entityClass, long id) {
        super(String.format("%s with id: %d does not exist", entityClass.getSimpleName(), id));
    }
}
