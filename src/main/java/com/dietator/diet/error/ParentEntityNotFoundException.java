package com.dietator.diet.error;

public class ParentEntityNotFoundException extends RuntimeException {

    public ParentEntityNotFoundException(Class parentEntity, Class childEntity, long id) {
        super(String.format("There is no %s with id: %d, so no %s stats can be generated",
                parentEntity.getSimpleName(),
                id,
                childEntity.getSimpleName()));
    }
}
