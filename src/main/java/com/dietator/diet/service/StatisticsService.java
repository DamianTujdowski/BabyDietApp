package com.dietator.diet.service;

import com.dietator.diet.projections.statistics_projections.MealConsumptionsCount;
import com.dietator.diet.projections.statistics_projections.MealConsumptionsNumber;
import com.dietator.diet.repository.ConsumptionTimeRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final MealRepository mealRepository;
    private final ConsumptionTimeRepository consumptionTimeRepository;

    public List<MealConsumptionsCount> getMealConsumptionsCount() {
        return mealRepository.countMealsConsumptions();
    }

    public MealConsumptionsNumber getMealConsumptionsNumber() {
        return consumptionTimeRepository.getMealConsumptionsNumber();
    }
}
