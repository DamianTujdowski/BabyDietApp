package com.dietator.diet.utils;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PredefinedMealCopier {

    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    public Set<Meal> copyPreDefinedMeals(Set<Meal> consumedMealsFromClient, Set<Meal> consumedMealsFromDb) {
        return removeCommonMeals(consumedMealsFromClient, consumedMealsFromDb)
                .stream()
                .map(this::copyAndSaveMealAndItsIngredients)
                .collect(Collectors.toSet());
    }

    private Set<Meal> removeCommonMeals(Set<Meal> consumedMealsFromClient, Set<Meal> consumedMealsFromDb) {
        Set<Meal> newMeals = new HashSet<>(consumedMealsFromClient);
        newMeals.removeAll(consumedMealsFromDb);
        return newMeals;
    }

    private Meal copyAndSaveMealAndItsIngredients(Meal meal) {
        Meal copiedMeal = new Meal(meal);
        if (meal.isPreDefined()) {
            copiedMeal.getIngredients().forEach(ingredientRepository::save);
            return mealRepository.save(copiedMeal);
        } else {
            return meal;
        }
    }

}
