package com.dietator.diet.projections;

import org.springframework.beans.factory.annotation.Value;

public interface IngredientBasicInfo {
    long getId();

    String getDesignation();

    @Value("#{@ingredientPreferencesFilter.filterPreferences(target)}")
    String getPreference();
}
