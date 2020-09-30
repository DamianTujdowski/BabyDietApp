package com.dietator.diet.error;

import com.dietator.diet.domain.Child;
import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.domain.Meal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityNotFoundExceptionTest {

    private EntityNotFoundException entityNotFoundException;

    @Test
    public void ChildEntityWithId10_shouldReturnProperMessage() {
        //given
        entityNotFoundException = new EntityNotFoundException(Child.class, 10);
        String expected = "Child with id: 10 does not exist";
        //when
        String message = entityNotFoundException.getMessage();
        //then
        assertEquals(expected, message);
    }

    @Test
    public void MealEntityWithId3_shouldReturnProperMessage() {
        //given
        entityNotFoundException = new EntityNotFoundException(Meal.class, 3);
        String expected = "Meal with id: 3 does not exist";
        //when
        String message = entityNotFoundException.getMessage();
        //then
        assertEquals(expected, message);
    }

    @Test
    public void IngredientEntityWithId24_shouldReturnProperMessage() {
        //given
        entityNotFoundException = new EntityNotFoundException(Ingredient.class, 24);
        String expected = "Ingredient with id: 24 does not exist";
        //when
        String message = entityNotFoundException.getMessage();
        //then
        assertEquals(expected, message);
    }

}