package com.dietator.diet.projections.statistics_projections;

import com.dietator.diet.domain.MealCategory;

public interface MealsPerCategoryNumber {

    MealCategory getCategory();

    int getMealsNumber();
}
