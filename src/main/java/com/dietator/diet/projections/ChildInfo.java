package com.dietator.diet.projections;

import java.time.LocalDateTime;
import java.util.Set;

public interface ChildInfo {
    long getId();

    String getFirstName();

    LocalDateTime getBirthDate();

    Set<MealBasicInfo> getConsumedMeals();

    Set<IngredientBasicInfo> getFavouriteAndDislikedIngredients();
}

