package com.dietator.diet.service;

import com.dietator.diet.domain.*;
import com.dietator.diet.repository.MealRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MealServiceTest {

    @InjectMocks
    private MealService mealService;
    @Mock
    private MealRepository mealRepositoryMock;
    private Meal noIngredientMealFromDb, potatoCocumberMealFromClient, twoIngredientMealFromDb, fourIngredientMealFromClient, fourIngredientMealFromDb, carrotSugarMealFromClient;

    @BeforeAll
    public void init() {
        Ingredient potato = new Ingredient(1L, "potato", 340, 180, false, false, false);
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false);
        Ingredient carrot = new Ingredient(3L, "carrot", 98, 70, true, false, false);
        Ingredient sugar = new Ingredient(4L, "sugar", 315, 14, false, false, false);
        Set<Ingredient> potatoCucumber = Stream.of(potato, cucumber).collect(Collectors.toSet());
        Set<Ingredient> carrotSugar = Stream.of(carrot, sugar).collect(Collectors.toSet());
        Set<Ingredient> fourIngredients = Stream.of(potato, cucumber, carrot, sugar).collect(Collectors.toSet());
        ConsumptionTime morning = new ConsumptionTime(1, LocalDateTime.of(2020, Month.APRIL, 13, 10, 40));
        ConsumptionTime afternoon = new ConsumptionTime(2, LocalDateTime.of(2020, Month.APRIL, 13, 15, 18));
        ConsumptionTime evening = new ConsumptionTime(3, LocalDateTime.of(2020, Month.APRIL, 13, 19, 5));
        Set<ConsumptionTime> consumptionTimes = Stream.of(morning, afternoon, evening).collect(Collectors.toSet());
        noIngredientMealFromDb = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false);
        potatoCocumberMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        carrotSugarMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), carrotSugar, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        twoIngredientMealFromDb = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        fourIngredientMealFromDb = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        fourIngredientMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false);
    }

    @Test
    public void whenAddingIngredientsToMealWithEmptyIngredientsSet_shouldAddAllIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(noIngredientMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(potatoCocumberMealFromClient);
        //then
        assertEquals(2, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMeal_shouldAddOnlyNewIngredientsToMealWhichHasIngredientsAlready() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientMealFromClient);
        //then
        assertEquals(4, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMeal_shouldAddAllIngredients_ifClientAndDatabaseMealHaveNoCommonIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(carrotSugarMealFromClient));
        //when
        Meal editedMeal = mealService.editMeal(potatoCocumberMealFromClient);
        //then
        assertEquals(4, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMeal_shouldNotAddIngredientsIfThereAreNoNewIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(fourIngredientMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientMealFromClient);
        //then
        assertEquals(4, editedMeal.getIngredients().size());
    }

}