package com.dietator.diet.service;

import com.dietator.diet.projections.statistics_projections.ConsumedMealsNumberAndDailyAverage;
import com.dietator.diet.projections.statistics_projections.MealsConsumptionQuantity;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final MealRepository mealRepository;

    public List<MealsConsumptionQuantity> getMealsConsumptionQuantity(long id, int page, int pageSize, Sort.Direction direction) {
        return mealRepository.getMealsConsumptionQuantity(
                id,
                PageRequest.of(page, pageSize, Sort.by(direction, "consumptionsQuantity"))
        );
    }

    public ConsumedMealsNumberAndDailyAverage getConsumedMealsNumberAndDailyAverage(long id) {
        return mealRepository.getConsumedMealsNumberAndDailyAverage(id);
    }
}
