package com.dietator.diet.projections.statistics_projections;

import java.time.LocalDate;

public interface DailyConsumedGrams {

    LocalDate getConsumptionDate();

    int getDailyConsumedGrams();
}
