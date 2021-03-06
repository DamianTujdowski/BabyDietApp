package com.dietator.diet.projections;

import com.dietator.diet.domain.MealCategory;
import com.dietator.diet.domain.PreparationDifficulty;

public interface MealBasicInfo {
    long getId();

    String getDesignation();

    int getEnergy();

    int getPreparationDuration();

    MealCategory getMealCategory();

    PreparationDifficulty getPreparationDifficulty();

}
