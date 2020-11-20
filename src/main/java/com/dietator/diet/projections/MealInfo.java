package com.dietator.diet.projections;

import com.dietator.diet.domain.ConsumptionTime;
import com.dietator.diet.domain.MealCategory;
import com.dietator.diet.domain.PreparationDifficulty;

import java.util.Set;

public interface MealInfo {
    long getId();

    String getDesignation();

    int getEnergy();

    int getPreparationDuration();

    Set<IngredientBasicInfo> getIngredients();

    Set<ConsumptionTime> getConsumptionTimes();

    MealCategory getMealCategory();

    PreparationDifficulty getPreparationDifficulty();

    boolean isPreDefined();

    long getChildId();
}
