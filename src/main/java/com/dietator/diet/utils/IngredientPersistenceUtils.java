package com.dietator.diet.utils;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class IngredientPersistenceUtils {

    private static IngredientRepository ingredientRepository;

    private IngredientPersistenceUtils() {
    }

    public static Set<Ingredient> clonePreDefinedIngredients(Set<Ingredient> clientIngredients, Set<Ingredient> dbIngredients) {
//        if (clientIngredients.containsAll(dbIngredients)) {
//            return new HashSet<>();
//        }
        return removeCommonIngredients(clientIngredients, dbIngredients)
                .stream()
                .map(IngredientPersistenceUtils::cloneAndSaveIngredient)
                .collect(Collectors.toSet());
    }

    private static Set<Ingredient> removeCommonIngredients(Set<Ingredient> clientIngredients, Set<Ingredient> dbIngredients) {
        clientIngredients.removeAll(dbIngredients);
        return clientIngredients;
    }

    private static Ingredient cloneAndSaveIngredient(Ingredient ingredient) {
        return ingredient.isPreDefined() ? ingredientRepository.save(new Ingredient(ingredient)) : ingredient;
    }

}
