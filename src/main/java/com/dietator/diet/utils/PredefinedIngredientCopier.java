package com.dietator.diet.utils;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PredefinedIngredientCopier {

    private final IngredientRepository ingredientRepository;

    public Set<Ingredient> copyPreDefinedIngredients(Set<Ingredient> clientIngredients, Set<Ingredient> dbIngredients) {
        return removeCommonIngredients(clientIngredients, dbIngredients)
                .stream()
                .map(this::copyAndSaveIngredient)
                .collect(Collectors.toSet());
    }

    private Set<Ingredient> removeCommonIngredients(Set<Ingredient> clientIngredients, Set<Ingredient> dbIngredients) {
        Set<Ingredient> newIngredients = new HashSet<>(clientIngredients);
        newIngredients.removeAll(dbIngredients);
        return newIngredients;
    }

    private Ingredient copyAndSaveIngredient(Ingredient ingredient) {
        return ingredient.isPreDefined() ? ingredientRepository.save(new Ingredient(ingredient)) : ingredient;
    }

}