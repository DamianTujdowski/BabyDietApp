package com.dietator.diet.projections;

import java.time.LocalDateTime;
import java.util.Set;

public interface ChildInfo {
    int getId();

    String getFirstName();

    LocalDateTime getBirthDate();

    Set<MealInfo> getConsumedMeals();

    Set<IngredientBasicInfo> getFavouriteAndDislikedIngredients();
}

