package com.dietator.diet.service;

import com.dietator.diet.projections.MealConsumptionsCount;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final MealRepository mealRepository;

    public List<MealConsumptionsCount> getMealConsumptionsCount() {
        return mealRepository.countMealsByConsumpitonsCount();
    }
}
