package com.dietator.diet.service;

import com.dietator.diet.domain.*;
import com.dietator.diet.repository.MealRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class MealServiceTest {

    @InjectMocks
    private MealService mealService;
    @Mock
    private MealRepository mealRepositoryMock;
    private  Meal noIngredientNoConTimesMealFromDb, potatoCucumberBeforeAfternoonMealFromClient,
            twoIngredientTwoConTimesMealFromDb, fourIngredientFourConTimesMealFromClient,
            fourIngredientFourConTimesMealFromDb, carrotSugarAfterAfternoonMealFromClient;

    @BeforeEach
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
        ConsumptionTime lateEvening = new ConsumptionTime(3, LocalDateTime.of(2020, Month.APRIL, 13, 21, 15));
        Set<ConsumptionTime> twoTimesBeforeAfternoon = Stream.of(morning, afternoon).collect(Collectors.toSet());
        Set<ConsumptionTime> twoTimesAfterAfternoon = Stream.of(evening, lateEvening).collect(Collectors.toSet());
        Set<ConsumptionTime> fourTimes = Stream.of(morning, afternoon, evening, lateEvening).collect(Collectors.toSet());
        noIngredientNoConTimesMealFromDb = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false);
        potatoCucumberBeforeAfternoonMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, twoTimesBeforeAfternoon, potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        carrotSugarAfterAfternoonMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, twoTimesAfterAfternoon, carrotSugar, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        twoIngredientTwoConTimesMealFromDb = new Meal(5L, "burrito", 800, "roll cake",
                10, twoTimesAfterAfternoon, potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        fourIngredientFourConTimesMealFromDb = new Meal(5L, "burrito", 800, "roll cake",
                10, fourTimes, fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        fourIngredientFourConTimesMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, fourTimes, fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false);
    }

    @Test
    public void whenEditingMealWithNoIngredients_shouldAddAllIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(noIngredientNoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(potatoCucumberBeforeAfternoonMealFromClient);
        //then
        assertEquals(2, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMealWhichHasIngredientsAlready_shouldAddOnlyNewIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromClient);
        //then
        assertEquals(4, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMeal_shouldAddAllIngredients_ifClientAndDatabaseMealHaveNoCommonIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(carrotSugarAfterAfternoonMealFromClient));
        //when
        Meal editedMeal = mealService.editMeal(potatoCucumberBeforeAfternoonMealFromClient);
        //then
        assertEquals(4, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMeal_shouldNotAddIngredientsIfThereAreNoNewIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(fourIngredientFourConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromClient);
        //then
        assertEquals(4, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMealWithNoConsumptionTimes_shouldAddAllConsumptionTimes() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(noIngredientNoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(carrotSugarAfterAfternoonMealFromClient);
        //then
        assertEquals(2, editedMeal.getConsumptionTime().size());
    }

    @Test
    public void whenEditingMealWhichHasConTimesAlready_shouldAddOnlyNewConTimes() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromDb);
        //then
        assertEquals(4, editedMeal.getConsumptionTime().size());
    }

    @Test
    public void whenEditingMeal_shouldAddAllConsumptionTimes_ifClientAndDatabaseMealHaveNoCommonConsumptionTimes() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(carrotSugarAfterAfternoonMealFromClient));
        //when
        Meal editedMeal = mealService.editMeal(potatoCucumberBeforeAfternoonMealFromClient);
        //then
        assertEquals(4, editedMeal.getConsumptionTime().size());
    }

    @Test
    public void whenEditingMeal_shouldNotAddConsumptionTimesIfThereAreNoNewConsumptionTimes() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(fourIngredientFourConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromClient);
        //then
        assertEquals(4, editedMeal.getConsumptionTime().size());
    }

}