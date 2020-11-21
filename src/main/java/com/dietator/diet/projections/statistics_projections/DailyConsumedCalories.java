package com.dietator.diet.projections.statistics_projections;

import java.time.LocalDate;

public interface DailyConsumedCalories {

    LocalDate getConsumptionDate();

    int getDailyCaloriesSum();
}
