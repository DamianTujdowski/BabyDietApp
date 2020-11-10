package com.dietator.diet.service;

import com.dietator.diet.projections.statistics_projections.MealsConsumptionQuantity;
import com.dietator.diet.projections.statistics_projections.AllConsumedMealsNumber;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final MealRepository mealRepository;

    public List<MealsConsumptionQuantity> getMealsConsumptionQuantity(long id) {
        return mealRepository.getMealsConsumptionQuantity(id);
    }

    public AllConsumedMealsNumber getAllConsumedMealsNumber(long id) {
        return mealRepository.getAllConsumedMealsNumber(id);
    }
}
