package com.dietator.diet.service;

import com.dietator.diet.domain.*;
import com.dietator.diet.repository.MealRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @InjectMocks
    private MealService mealService;
    @Mock
    private MealRepository mealRepositoryMock;
    @Mock
    private PredefinedIngredientCopyingService predefinedIngredientCopyingService;

    private Meal noIngredientNoConTimesMealFromDb, potatoCucumberBeforeAfternoonMealFromClient,
            twoIngredientTwoConTimesMealFromDb, fourIngredientFourConTimesMealFromClient,
            fourIngredientFourConTimesMealFromDb, carrotSugarAfterAfternoonMealFromClient,
            fourIngredientMealWithTwoPredefinedIngredientsFromClient;

    @BeforeEach
    public void init() {
        Ingredient potato = new Ingredient(1L, "potato", 340, 180, false, false, false);
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false);
        Ingredient carrot = new Ingredient(3L, "carrot", 98, 70, true, false, false);
        Ingredient carrotPredefined = new Ingredient(3L, "carrotPredefined", 98, 70, true, false, true);
        Ingredient sugar = new Ingredient(4L, "sugar", 315, 14, false, false, false);
        Ingredient sugarPredefined = new Ingredient(4L, "sugarPredefined", 315, 14, false, false, true);
        Set<Ingredient> potatoCucumber = Stream.of(potato, cucumber).collect(Collectors.toSet());
        Set<Ingredient> carrotSugar = Stream.of(carrot, sugar).collect(Collectors.toSet());
        Set<Ingredient> fourIngredients = Stream.of(potato, cucumber, carrot, sugar).collect(Collectors.toSet());
        Set<Ingredient> fourIngredientsWithTwoPredefined = Stream.of(potato, cucumber, carrotPredefined, sugarPredefined).collect(Collectors.toSet());
        ConsumptionTime morning = new ConsumptionTime(1, LocalDateTime.of(2020, Month.APRIL, 13, 10, 40));
        ConsumptionTime afternoon = new ConsumptionTime(2, LocalDateTime.of(2020, Month.APRIL, 13, 15, 18));
        ConsumptionTime evening = new ConsumptionTime(3, LocalDateTime.of(2020, Month.APRIL, 13, 19, 5));
        ConsumptionTime lateEvening = new ConsumptionTime(3, LocalDateTime.of(2020, Month.APRIL, 13, 21, 15));
        Set<ConsumptionTime> twoTimesBeforeAfternoon = Stream.of(morning, afternoon).collect(Collectors.toSet());
        Set<ConsumptionTime> twoTimesAfterAfternoon = Stream.of(evening, lateEvening).collect(Collectors.toSet());
        Set<ConsumptionTime> fourTimes = Stream.of(morning, afternoon, evening, lateEvening).collect(Collectors.toSet());
        noIngredientNoConTimesMealFromDb = new Meal(5L, "burrito", 0, "roll cake",
                10, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        potatoCucumberBeforeAfternoonMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, twoTimesBeforeAfternoon, potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        carrotSugarAfterAfternoonMealFromClient = new Meal(5L, "burrito", 113, "roll cake",
                10, twoTimesAfterAfternoon, carrotSugar, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        twoIngredientTwoConTimesMealFromDb = new Meal(5L, "burrito", 762, "roll cake",
                10, twoTimesAfterAfternoon, potatoCucumber, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        fourIngredientFourConTimesMealFromDb = new Meal(5L, "burrito", 875, "roll cake",
                10, fourTimes, fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        fourIngredientFourConTimesMealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, fourTimes, fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        fourIngredientMealWithTwoPredefinedIngredientsFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, fourTimes, fourIngredientsWithTwoPredefined, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
    }

    @Test
    public void whenEditingMealWithNoIngredients_shouldAddAllIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(noIngredientNoConTimesMealFromDb));
        when(predefinedIngredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(potatoCucumberBeforeAfternoonMealFromClient.getIngredients());
        //when
        Meal editedMeal = mealService.editMeal(potatoCucumberBeforeAfternoonMealFromClient);
        //then
        assertEquals(2, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMealWhichHasIngredientsAlready_shouldAddOnlyNewIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        when(predefinedIngredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(fourIngredientFourConTimesMealFromClient.getIngredients());
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromClient);
        //then
        assertEquals(4, editedMeal.getIngredients().size());
    }

    @Test
    public void whenEditingMeal_shouldAddAllIngredients_ifClientAndDatabaseMealHaveNoCommonIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(carrotSugarAfterAfternoonMealFromClient));
        when(predefinedIngredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(potatoCucumberBeforeAfternoonMealFromClient.getIngredients());
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
        assertEquals(2, editedMeal.getConsumptionTimes().size());
    }

    @Test
    public void whenEditingMealWhichHasConTimesAlready_shouldAddOnlyNewConTimes() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromDb);
        //then
        assertEquals(4, editedMeal.getConsumptionTimes().size());
    }

    @Test
    public void whenEditingMeal_shouldAddAllConsumptionTimes_ifClientAndDatabaseMealHaveNoCommonConsumptionTimes() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(carrotSugarAfterAfternoonMealFromClient));
        //when
        Meal editedMeal = mealService.editMeal(potatoCucumberBeforeAfternoonMealFromClient);
        //then
        assertEquals(4, editedMeal.getConsumptionTimes().size());
    }

    @Test
    public void whenEditingMeal_shouldNotAddConsumptionTimesIfThereAreNoNewConsumptionTimes() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(fourIngredientFourConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromClient);
        //then
        assertEquals(4, editedMeal.getConsumptionTimes().size());
    }

    @Test
    public void whenEditingMeal_shouldCopyPredefinedIngredients() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        Set<Ingredient> editedIngredients = fourIngredientMealWithTwoPredefinedIngredientsFromClient.getIngredients()
                .stream()
                .map(ingredient -> ingredient.isPreDefined() ? new Ingredient(ingredient) : ingredient)
                .collect(Collectors.toSet());
        when(predefinedIngredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(editedIngredients);
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientMealWithTwoPredefinedIngredientsFromClient);
        //then
        assertEquals(4, editedMeal.getConsumptionTimes().size());
    }

    @Test
    public void whenEditingMealAndCopyingPredefinedIngredients_shouldSetIsPredefinedFieldValueToFalse() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        Set<Ingredient> editedIngredients = fourIngredientMealWithTwoPredefinedIngredientsFromClient.getIngredients()
                .stream()
                .map(ingredient -> ingredient.isPreDefined() ? new Ingredient(ingredient) : ingredient)
                .collect(Collectors.toSet());
        when(predefinedIngredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(editedIngredients);
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientMealWithTwoPredefinedIngredientsFromClient);
        long predefinedIngredientsNumber = editedMeal.getIngredients()
                .stream()
                .filter(Ingredient::isPreDefined)
                .count();
        //then
        assertEquals(0, predefinedIngredientsNumber);
    }

    @Test
    public void whenEditingMealsWithAddedPredefinedIngredients_changesInThoseIngredients_shouldNotReflectInOtherMealIngredients() {
        //given
        Ingredient carrot = new Ingredient(3L, "carrotPredefined", 98, 70, true, false, true);
        Set<Ingredient> mealOneIngredients = Stream.of(carrot).collect(Collectors.toSet());
        Meal mealOne = new Meal(1L, "burrito", 800, "roll cake",
                10, new HashSet<>(), mealOneIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        Meal mealOneFromDb = new Meal(1L, "burrito", 800, "roll cake",
                10, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        Set<Ingredient> mealTwoIngredients = Stream.of(carrot).collect(Collectors.toSet());
        Meal mealTwo = new Meal(2L, "burrito", 800, "roll cake",
                10, new HashSet<>(), mealTwoIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);
        Meal mealTwoFromDb = new Meal(2L, "burrito", 800, "roll cake",
                10, new HashSet<>(), new HashSet<>(), MealCategory.DINNER, PreparationDifficulty.EASY, false, 1);

        when(mealRepositoryMock.findById(1L)).thenReturn(Optional.of(mealOneFromDb));
        Set<Ingredient> mealOneEditedIngredients = mealOne.getIngredients()
                .stream()
                .map(ingredient -> ingredient.isPreDefined() ? new Ingredient(ingredient) : ingredient)
                .collect(Collectors.toSet());
        when(predefinedIngredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(mealOneEditedIngredients);

        when(mealRepositoryMock.findById(2L)).thenReturn(Optional.of(mealTwoFromDb));
        Set<Ingredient> mealTwoEditedIngredients = mealTwo.getIngredients()
                .stream()
                .map(ingredient -> ingredient.isPreDefined() ? new Ingredient(ingredient) : ingredient)
                .collect(Collectors.toSet());
        when(predefinedIngredientCopyingService.copyPreDefinedIngredients(anySet(), anySet()))
                .thenReturn(mealTwoEditedIngredients);

        //when
        Meal editedMealOne = mealService.editMeal(mealOne);
        editedMealOne.getIngredients().forEach(ingredient -> ingredient.setDesignation("mealOneIngredient"));
        String mealOneIngredientDesignation = editedMealOne.getIngredients()
                .stream()
                .map(Ingredient::getDesignation)
                .findFirst()
                .get();
        Meal editedMealTwo = mealService.editMeal(mealTwo);
        editedMealTwo.getIngredients().forEach(ingredient -> ingredient.setDesignation("mealTwoIngredient"));
        String mealTwoIngredientDesignation = editedMealTwo.getIngredients()
                .stream()
                .map(Ingredient::getDesignation)
                .findFirst()
                .get();
        //then
        assertNotEquals(mealOneIngredientDesignation, mealTwoIngredientDesignation);
    }

    @Test
    public void whenAddingNewIngredientsToMeal_shouldUpdateMealsEnergyValue() {
        //given
        when(mealRepositoryMock.findById(5L)).thenReturn(Optional.of(twoIngredientTwoConTimesMealFromDb));
        //when
        Meal editedMeal = mealService.editMeal(fourIngredientFourConTimesMealFromDb);
        //then
        assertEquals(875, editedMeal.getEnergy());
    }
}