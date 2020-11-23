package com.dietator.diet.service;

import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdatingMealEnergyValueFieldTest {

    @InjectMocks
    private IngredientService ingredientService;
    @Mock
    private MealRepository mealRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    @BeforeEach
    private void init() {

    }

    @Test
    public void shouldDoSth() {

    }


}
