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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdatingMealEnergyValueFieldTest {

    @InjectMocks
    private IngredientService ingredientService;
    @Mock
    private IngredientRepository ingredientRepositoryMock;

    @InjectMocks
    private MealService mealService;
    @Mock
    private MealRepository mealRepositoryMock;
    @Mock
    private PredefinedIngredientCopyingService predefinedIngredientCopyingServiceMock;

    private Meal noIngredientNoConTimesMealFromDb, potatoCucumberBeforeAfternoonMealFromClient,
            twoIngredientTwoConTimesMealFromDb, fourIngredientFourConTimesMealFromClient,
            fourIngredientFourConTimesMealFromDb, carrotSugarAfterAfternoonMealFromClient,
            fourIngredientMealWithTwoPredefinedIngredientsFromClient;

    private Ingredient potato;

    @BeforeEach
    private void init() {
        potato = new Ingredient(1L, "potato", 340, 180, false, false, false, 5L);
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false, 5L);
        Ingredient carrot = new Ingredient(3L, "carrot", 98, 70, true, false, false, 5L);
        Ingredient sugar = new Ingredient(4L, "sugar", 315, 14, false, false, false, 5L);
        Set<Ingredient> potatoCucumber = Stream.of(potato, cucumber).collect(Collectors.toSet());
        Set<Ingredient> carrotSugar = Stream.of(carrot, sugar).collect(Collectors.toSet());
        Set<Ingredient> fourIngredients = Stream.of(potato, cucumber, carrot, sugar).collect(Collectors.toSet());
        noIngredientNoConTimesMealFromDb = new Meal(5L, "burrito", 0, "roll cake",
                10, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        potatoCucumberBeforeAfternoonMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        carrotSugarAfterAfternoonMealFromClient = new Meal(5L, "burrito", 113, "roll cake",
                10, new HashSet<>(), carrotSugar, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        twoIngredientTwoConTimesMealFromDb = new Meal(5L, "burrito", 762, "roll cake",
                10, new HashSet<>(), potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        fourIngredientFourConTimesMealFromDb = new Meal(5L, "burrito", 0, "roll cake",
                10, new HashSet<>(), fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        fourIngredientFourConTimesMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
    }

    @Test
    public void whenNotChangingIngredients_shouldNotChangeMealEnergyValue() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(twoIngredientTwoConTimesMealFromDb);
        //then
        assertEquals(762, editedMeal.getEnergy());
    }

    @Test
    public void whenAddingNewIngredientsToMeal_shouldUpdateMealEnergyValue() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(carrotSugarAfterAfternoonMealFromClient);
        //then
        assertEquals(875, editedMeal.getEnergy());
    }

    @Test
    public void whenMealFromDBContainsCommonIngredientsWithMealFromClient_andItsEnergyValueIsZero_shouldUpdateMealEnergyValue() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromDb);
        //then
        assertEquals(875, editedMeal.getEnergy());
    }

    @Test
    public void whenDeletingIngredient_ShouldUpdateMealEnergyValue() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        when(ingredientRepositoryMock.findById(1L)).thenReturn(Optional.of(potato));
        Meal editedMeal = twoIngredientTwoConTimesMealFromDb;
        System.out.println(editedMeal.getEnergy());
        System.out.println(editedMeal.getIngredients().size());
        //when
        ingredientService.delete(1);
        System.out.println(editedMeal.getEnergy());
        System.out.println(editedMeal.getIngredients().size());
        //then
        assertEquals(150, editedMeal.getEnergy());
    }

    @Test
    public void whenUpdatingIngredientEnergyPer100Grams_shouldUpdateMealEnergyValue() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        when(ingredientRepositoryMock.findById(1L)).thenReturn(Optional.of(potato));
        Meal meal = twoIngredientTwoConTimesMealFromDb;
        System.out.println(meal.getEnergy());
        System.out.println(meal.getIngredients().size());
        //when
        potato.setEnergyPer100Grams(500);
        ingredientService.edit(potato);
        System.out.println(meal.getEnergy());
        System.out.println(meal.getIngredients().size());
        //then
        assertEquals(1050, meal.getEnergy());
    }

    @Test
    public void print() {
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false, 5L);
        Ingredient carrot = new Ingredient(3L, "carrot", 98, 70, true, false, false, 5L);
        Ingredient sugar = new Ingredient(4L, "sugar", 315, 14, false, false, false, 5L);
        Set<Ingredient> ingredients = Stream.of(cucumber, carrot, sugar).collect(Collectors.toSet());
        System.out.println(ingredients.size());
        sugar.setEnergyPer100Grams(1000);
        ingredients.remove(sugar);
        System.out.println(ingredients.size());

    }

}
