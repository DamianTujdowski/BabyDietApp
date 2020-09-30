package com.dietator.diet.domain;

import org.springframework.stereotype.Component;

@Component
public class IngredientPreferencesFilter {

    public String filterPreferences(Ingredient ingredient) {
        return ingredient.isFavourite() ? "Favourite" : ingredient.isDisliked() ? "Disliked" : "Neutral";
    }

}
