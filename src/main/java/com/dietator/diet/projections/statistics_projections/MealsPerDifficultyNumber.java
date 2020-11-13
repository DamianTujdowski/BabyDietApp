package com.dietator.diet.projections.statistics_projections;

import com.dietator.diet.domain.PreparationDifficulty;

public interface MealsPerDifficultyNumber {

    PreparationDifficulty getDifficulty();

    int getMealsNumber();

}
