package com.dietator.diet.projections;

import java.time.LocalDate;
import java.util.Set;

public interface ChildInfo {
    long getId();

    String getFirstName();

    LocalDate getBirthDate();

    Set<MealBasicInfo> getConsumedMeals();

    Set<IngredientBasicInfo> getFavouriteAndDislikedIngredients();
}

