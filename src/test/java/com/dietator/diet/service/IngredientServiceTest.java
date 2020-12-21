package com.dietator.diet.service;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.utils.MealEnergyValueUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private MealEnergyValueUpdater mealEnergyUpdater;

    private Ingredient ingredient;

    @BeforeEach()
    public void init() {
        ingredient = new Ingredient(500L, "", 340, 180, false, false, false, 5L);
    }

    @Test
    public void shouldThrowEntityNotFoundException_whenEntityWithGivenIdDoesNotExistInDatabase() {
        //given
        Exception exception = assertThrows(RuntimeException.class, () -> ingredientService.findIngredientById(500));
        String expectedMessage = "Ingredient with id: 500 does not exist";
        String actualMessage = exception.getMessage();
        //when

        //then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldThrowEntityNotFoundException_whenTryingToEditIngredientWithIdThatDoesNotExistsInDatabase() {
        //given
        Exception exception = assertThrows(RuntimeException.class, () -> ingredientService.edit(ingredient));
        String expectedMessage = "Ingredient with id: 500 does not exist";
        String actualMessage = exception.getMessage();
        //when

        //then
        assertEquals(expectedMessage, actualMessage);
    }

}