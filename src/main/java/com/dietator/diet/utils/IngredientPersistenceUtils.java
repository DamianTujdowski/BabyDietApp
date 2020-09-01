package com.dietator.diet.utils;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.repository.IngredientRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class IngredientPersistenceUtils {

    private static IngredientRepository ingredientRepository;

    public static Set<Ingredient> filterNewIngredients(Set<Ingredient> ingredientsFromUser, Set<Ingredient> ingredientsFromDb) {
        ingredientsFromUser.removeAll(ingredientsFromDb);
        return ingredientsFromUser;
    }

    public static Set<Ingredient> clonePreDefinedIngredients(Set<Ingredient> favouriteAndDislikedIngredients) {
        return favouriteAndDislikedIngredients
                .stream()
                .map(IngredientPersistenceUtils::cloneAndSaveIngredient)
                .collect(Collectors.toSet());
    }

    private static Ingredient cloneAndSaveIngredient(Ingredient ingredient) {
        return ingredient.isPreDefined() ? ingredientRepository.save(new Ingredient(ingredient)) : ingredient;
    }

}
