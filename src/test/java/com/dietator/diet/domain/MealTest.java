package com.dietator.diet.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    private Meal meal, clonedMeal;

    @BeforeEach
    public void initIngredientsAndConsumptionTime() {
        Ingredient potato = new Ingredient(1L, "potato", 340, 180, false, false, false);
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false);
        Set<Ingredient> ingredients = Stream.of(potato, cucumber).collect(Collectors.toSet());
        ConsumptionTime morning = new ConsumptionTime(1, LocalDateTime.of(2020, Month.APRIL, 13, 10, 40));
        ConsumptionTime afternoon = new ConsumptionTime(2, LocalDateTime.of(2020, Month.APRIL, 13, 15, 18));
        ConsumptionTime evening = new ConsumptionTime(3, LocalDateTime.of(2020, Month.APRIL, 13, 19, 5));
        Set<ConsumptionTime> consumptionTimes = Stream.of(morning, afternoon, evening).collect(Collectors.toSet());
        meal = new Meal(1L, "vegetable salad", 420, "chop chop amd put in pot",
                10, consumptionTimes, ingredients, MealCategory.BREAKFAST, PreparationDifficulty.EASY, true);
        clonedMeal = new Meal(meal);
    }

    @Test
    public void whenModifyingOriginalObject_copyShouldNotChange() {
        //given

        //when
        meal.setPreparationDescription("chop chop then drop");
        //then
        assertNotEquals(meal.getPreparationDescription(), clonedMeal.getPreparationDescription());
    }

    @Test
    public void whenModifyingOriginalObjectsIngredientsCollection_copyIngredientsCollectionShouldNotChange() {
        //given

        //when
        Ingredient potato = meal.getIngredients().stream().filter(m -> m.getDesignation().equals("potato")).findFirst().get();
        potato.setEnergyPer100Grams(1000);
        Ingredient clonedPotato = clonedMeal.getIngredients().stream().filter(m -> m.getDesignation().equals("potato")).findFirst().get();
        //then
        assertNotEquals(potato.getEnergyPer100Grams(), clonedPotato.getEnergyPer100Grams());
    }

    @Test
    public void copiedObjectShouldHaveZeroAsIdFieldValue() {
        //given

        //when

        //then
        assertEquals(0, clonedMeal.getId());
    }

    @Test
    public void copiedObjectIngredientCollections_shouldHaveZeroAsIdFieldValue() {
        //given

        //when

        //then
        assertEquals(0, clonedMeal.getIngredients().iterator().next().getId());
    }

    @Test
    public void clonedMealShouldHaveIsPrePreparedFieldSetToFalse() {
        //given

        //when

        //then
        assertFalse(clonedMeal.isPreDefined());
    }

}