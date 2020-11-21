package com.dietator.diet.projections.statistics_projections;

import java.time.LocalDate;

public interface DailyCookingTime {

    LocalDate getConsumptionDate();

    int getDailyCookingTime();
}
