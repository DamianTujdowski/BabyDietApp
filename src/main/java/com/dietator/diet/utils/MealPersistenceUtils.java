package com.dietator.diet.utils;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class MealPersistenceUtils {

    private static MealRepository mealRepository;
    private static IngredientRepository ingredientRepository;

    private MealPersistenceUtils() {
    }

    public static Set<Meal> clonePreDefinedMeals(Set<Meal> consumedMealsFromUser, Set<Meal> consumedMealsFromDb) {
        return filterNewMeals(consumedMealsFromUser, consumedMealsFromDb)
                .stream()
                .map(MealPersistenceUtils::cloneAndSaveMeal)
                .collect(Collectors.toSet());
    }

    private static Set<Meal> filterNewMeals(Set<Meal> consumedMealsFromUser, Set<Meal> consumedMealsFromDb) {
        consumedMealsFromUser.removeAll(consumedMealsFromDb);
        return consumedMealsFromUser;
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
