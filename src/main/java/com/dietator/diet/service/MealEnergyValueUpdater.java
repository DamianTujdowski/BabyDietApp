package com.dietator.diet.service;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class MealEnergyValueUpdater {

    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    private void updateMealEnergyValue(Ingredient editedIngredient) {
        Meal meal = findById(editedIngredient.getMealId());
        Set<Ingredient> ingredients = meal.getIngredients();
        updateIngredientEnergy(ingredients, editedIngredient);
        meal.setEnergy(countMealEnergy(ingredients));
    }

    private void updateIngredientEnergy(Set<Ingredient> ingredients, Ingredient editedIngredient) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == editedIngredient.getId()) {
                ingredient.setEnergyPer100Grams(editedIngredient.getEnergyPer100Grams());
            }
        }
    }

    private void updateMealEnergyValue(long id) {
        Ingredient toDelete = findIngredientById(id);
        Meal meal = findById(toDelete.getMealId());
        Set<Ingredient> ingredients = meal.getIngredients();
        ingredients.remove(toDelete);
        meal.setEnergy(countMealEnergy(ingredients));
    }

    private Meal findById(long id) {
        return mealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Meal.class, id));
    }

    private int countMealEnergy(Set<Ingredient> ingredients) {
        return ingredients
                .stream()
                .mapToInt(this::countEnergyPerMeal)
                .sum();
    }

    private int countEnergyPerMeal(Ingredient ingredient) {
        return (int) Math.round(ingredient.getWeightPerMeal() / 100.0 * ingredient.getEnergyPer100Grams());
    }

    public Ingredient findIngredientById(long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Ingredient.class, id));
    }
}
