package com.dietator.diet.utils;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientCopyingService {

    private IngredientRepository ingredientRepository;

    public Set<Ingredient> clonePreDefinedIngredients(Set<Ingredient> clientIngredients, Set<Ingredient> dbIngredients) {
        return removeCommonIngredients(clientIngredients, dbIngredients)
                .stream()
                .map(this::cloneAndSaveIngredient)
                .collect(Collectors.toSet());
    }

    private Set<Ingredient> removeCommonIngredients(Set<Ingredient> clientIngredients, Set<Ingredient> dbIngredients) {
        clientIngredients.removeAll(dbIngredients);
        return clientIngredients;
    }

    private Ingredient cloneAndSaveIngredient(Ingredient ingredient) {
        return ingredient.isPreDefined() ? ingredientRepository.save(new Ingredient(ingredient)) : ingredient;
    }

}
