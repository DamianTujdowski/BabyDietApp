package com.dietator.diet.service;

import com.dietator.diet.projections.statistics_projections.MealsConsumptionQuantity;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final MealRepository mealRepository;

    public List<MealsConsumptionQuantity> getMealsConsumptionQuantity(long id, int page, int pageSize) {
        return mealRepository.getMealsConsumptionQuantity(id, PageRequest.of(page, pageSize));
    }

    public long getAllConsumedMealsNumber(long id) {
        return mealRepository.getAllConsumedMealsNumber(id);
    }
}
