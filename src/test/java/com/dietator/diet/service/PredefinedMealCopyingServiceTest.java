package com.dietator.diet.service;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.domain.MealCategory;
import com.dietator.diet.domain.PreparationDifficulty;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PredefinedMealCopyingServiceTest {

    @InjectMocks
    private PredefinedMealCopyingService mealCopyingService;
    @Mock
    private MealRepository mealRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    private Meal predefined, mealThree, mealFour;
    private Set<Meal> onePredefinedMeal, mealThreeFourSet;

    @BeforeEach
    public void init() {
        Ingredient ingredientOne = new Ingredient(1L, "potato", 340, 180, false, false, true);
        Ingredient ingredientTwo = new Ingredient(2L, "cucumber", 150, 100, true, false, true);
        Ingredient ingredientThree = new Ingredient(3L, "carrot", 98, 70, true, false, false);
        Ingredient ingredientFour = new Ingredient(4L, "sugar", 315, 14, false, false, false);
        Set<Ingredient> ingredientsOneTwoSet = Stream.of(ingredientOne, ingredientTwo).collect(Collectors.toSet());
        Set<Ingredient> ingredientsThreeFourSet = Stream.of(ingredientThree, ingredientFour).collect(Collectors.toSet());
        Set<Ingredient> fourIngredientsSet = Stream.of(ingredientOne, ingredientTwo, ingredientThree, ingredientFour).collect(Collectors.toSet());
        predefined = new Meal(1, "predefined meal", 420, "chop chop amd put in pot",
                10, new HashSet<>(), ingredientsOneTwoSet, MealCategory.BREAKFAST, PreparationDifficulty.EASY, true);
        mealThree = new Meal(3, "meal three", 420, "chop chop slowly and precisely",
                24, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false);
        mealFour = new Meal(4, "meal four", 420, "chop chop slowly and precisely",
                24, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false);
        onePredefinedMeal = Stream.of(predefined).collect(Collectors.toSet());
        mealThreeFourSet = Stream.of(mealThree, mealFour).collect(Collectors.toSet());

    }

    @Test
    public void shouldCopyPredefinedMeal() {
        //given
        when(mealRepository.save(any(Meal.class))).thenReturn(new Meal(predefined))
                .thenReturn(mealThree)
                .thenReturn(mealFour);
        //when
        Set<Meal> copiedMeals = mealCopyingService.copyPreDefinedMeals(onePredefinedMeal, mealThreeFourSet);
        Meal copiedMeal = copiedMeals
                .stream()
                .filter(meal -> meal.getDesignation().equals("predefined meal"))
                .findFirst()
                .get();
        //then
        assertNotEquals(copiedMeal, predefined);
    }

    @Test
    public void shouldCopyPredefinedMealAndItsIngredients() {
        //given
        List<Ingredient> ingredients = new ArrayList<>(predefined.getIngredients());
        when(mealRepository.save(any(Meal.class)))
                .thenReturn(new Meal(predefined))
                .thenReturn(mealThree)
                .thenReturn(mealFour);
        when(ingredientRepository.save(any(Ingredient.class)))
                .thenReturn(new Ingredient(ingredients.get(0)))
                .thenReturn(new Ingredient(ingredients.get(1)));
        //when
        Set<Meal> copiedMeals = mealCopyingService.copyPreDefinedMeals(onePredefinedMeal, mealThreeFourSet);
        Meal copiedMeal = copiedMeals
                .stream()
                .filter(meal -> meal.getDesignation().equals("predefined meal"))
                .findFirst()
                .get();
        Set<Ingredient> copiedIngredients = copiedMeal.getIngredients()
                .stream().filter(ingredient -> !ingredient.isPreDefined())
                .collect(Collectors.toSet());
        //then
        assertEquals(2, copiedIngredients.size());
    }

}