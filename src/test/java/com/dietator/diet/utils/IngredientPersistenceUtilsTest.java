package com.dietator.diet.utils;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.domain.MealCategory;
import com.dietator.diet.domain.PreparationDifficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dietator.diet.utils.IngredientPersistenceUtils.clonePreDefinedIngredients;
import static org.junit.jupiter.api.Assertions.*;

class IngredientPersistenceUtilsTest {

    private Meal mealFromDb, mealFromClient;
    private Set<Ingredient> noIngredients, twoIngredients, fourIngredients;

    @BeforeEach
    public void init() {
        Ingredient potato = new Ingredient(1L, "potato", 340, 180, false, false, false);
        Ingredient cucumber = new Ingredient(2L, "cucumber", 150, 100, true, false, false);
        Ingredient carrot = new Ingredient(3L, "carrot", 98, 70, true, false, false);
        Ingredient sugar = new Ingredient(4L, "sugar", 315, 14, false, false, false);
        noIngredients = new HashSet<>();
        twoIngredients = Stream.of(potato, cucumber).collect(Collectors.toSet());
        fourIngredients = Stream.of(potato, cucumber, carrot, sugar).collect(Collectors.toSet());
        mealFromDb = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false);
        mealFromClient = new Meal(5L, "burrito", 800, "roll cake",
                10, new HashSet<>(), fourIngredients, MealCategory.DINNER, PreparationDifficulty.EASY, false);
    }

    @Test
    public void shouldRemoveNoIngredients() {
        //given

        //when
        Set<Ingredient> result = clonePreDefinedIngredients(fourIngredients, noIngredients);
        //then
        assertEquals(4, result.size());
    }

    @Test
    public void shouldRemoveTwoIngredients() {
        //given

        //when
        Set<Ingredient> result = clonePreDefinedIngredients(fourIngredients, twoIngredients);
        //then
        assertEquals(2, result.size());
    }

    @Test
    public void shouldAddNoIngredients() {
        //given
        Set<Ingredient> scl = mealFromClient.getIngredients();
        Set<Ingredient> sdb = mealFromDb.getIngredients();
        scl.removeAll(sdb);
        Set<Ingredient> emptySet = new HashSet<>();
        //when
        mealFromDb.getIngredients().addAll(emptySet);
//        mealFromDb.getIngredients().addAll(clonePreDefinedIngredients(mealFromClient.getIngredients(), mealFromDb.getIngredients()));
        //then
        assertEquals(4, mealFromDb.getIngredients().size());
    }

    @Test
    void print() {
        Set<Integer> one = Stream.of(1, 2, 3).collect(Collectors.toSet());
        Set<Integer> two = Stream.of(4, 5, 6).collect(Collectors.toSet());
//        Set<Integer> three = Stream.of(4, 5, 6).collect(Collectors.toSet());
        Set<Ingredient> ing = clonePreDefinedIngredients(mealFromClient.getIngredients(), mealFromDb.getIngredients());
        System.out.println(ing.size());
    }

}