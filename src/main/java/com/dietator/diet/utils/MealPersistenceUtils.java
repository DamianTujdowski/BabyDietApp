package com.dietator.diet.utils;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class MealPersistenceUtils {

    private static MealRepository mealRepository;
    private static IngredientRepository ingredientRepository;

    public static Set<Meal> filterNewMeals(Set<Meal> consumedMealsFromUser, Set<Meal> consumedMealsFromDb) {
        consumedMealsFromUser.removeAll(consumedMealsFromDb);
        return consumedMealsFromUser;
    }

    public static Set<Meal> clonePreDefinedMeals(Set<Meal> consumedMeals) {
        return consumedMeals
                .stream()
                .map(MealPersistenceUtils::cloneAndSaveMeal)
                .collect(Collectors.toSet());
    }

    private static Meal cloneAndSaveMeal(Meal meal) {
        Meal clonedMeal = new Meal(meal);
        if (meal.isPreDefined()) {
            clonedMeal.getIngredients().forEach(ingredientRepository::save);
            return mealRepository.save(clonedMeal);
        } else {
            return meal;
        }
    }

}
