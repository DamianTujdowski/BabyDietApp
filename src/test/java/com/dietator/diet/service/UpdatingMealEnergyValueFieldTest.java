package com.dietator.diet.service;

import com.dietator.diet.domain.*;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatingMealEnergyValueFieldTest {

    @InjectMocks
    private MealEnergyValueUpdater mealEnergyUpdater;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private MealRepository mealRepository;

    private Meal potatoCucumberMeal, fourIngredientsMeal;

    private Ingredient potato;

    @BeforeEach
    private void init() {
        potato = new Ingredient(1L, "potato", 340, 180, false, false, false, 5L);
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false, 5L);
        Ingredient carrot = new Ingredient(3L, "carrot", 98, 70, true, false, false, 5L);
        Ingredient sugar = new Ingredient(4L, "sugar", 315, 14, false, false, false, 5L);
        Set<Ingredient> potatoCucumber = Stream.of(potato, cucumber).collect(Collectors.toSet());
        Set<Ingredient> fourIngredients = Stream.of(potato, cucumber, carrot, sugar).collect(Collectors.toSet());
        potatoCucumberMeal = new Meal(5L, "burrito", 762, "roll cake",
                10, new HashSet<>(), potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        fourIngredientsMeal = new Meal(6L, "burrito", 0, "roll cake",
                10, new HashSet<>(), fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
    }

    @Test
    void whenAddingNewIngredientsToMeal_shouldCountMealNewEnergyValue() {
        //given
        Meal edited = potatoCucumberMeal;
        Meal fromClient = fourIngredientsMeal;
        //when
        int updatedMealEnergy = mealEnergyUpdater.countMealEnergyValue(edited, fromClient);
        //then
        assertEquals(875, updatedMealEnergy);
    }

    @Test
    void whenNotChangingIngredientsEnergyOrWeightPerMeal_shouldNotChangeMealEnergyValue() {
        //given
        when(mealRepository.findById(5L)).thenReturn(Optional.of(potatoCucumberMeal));
        Meal editedMeal = potatoCucumberMeal;
        //when
        mealEnergyUpdater.updateMealEnergyValue(potato);
        //then
        assertEquals(762, editedMeal.getEnergy());
    }

    @Test
    void whenUpdatingIngredientEnergyPer100Grams_shouldUpdateMealEnergyValue() {
        //given
        when(mealRepository.findById(5L)).thenReturn(Optional.of(potatoCucumberMeal));
        Meal editedMeal = potatoCucumberMeal;
        //when
        potato.setEnergyPer100Grams(400);
        mealEnergyUpdater.updateMealEnergyValue(potato);
        //then
        assertEquals(870, editedMeal.getEnergy());
    }

    @Test
    void whenDeletingIngredient_ShouldUpdateMealEnergyValue() {
        //given
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(potato));
        when(mealRepository.findById(5L)).thenReturn(Optional.of(potatoCucumberMeal));
        Meal editedMeal = potatoCucumberMeal;
        //when
        mealEnergyUpdater.updateMealEnergyValue(1);
        //then
        assertEquals(150, editedMeal.getEnergy());
    }

}
