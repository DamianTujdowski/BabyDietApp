package com.dietator.diet.service;

import com.dietator.diet.projections.statistics_projections.*;
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

    //TODO secure before number page smaller than zero

    public List<MealsConsumptionQuantity> getMealsConsumptionQuantity(long id,
                                                                      int pageNumber,
                                                                      int pageSize,
                                                                      Sort.Direction direction) {
        return mealRepository.countMealsConsumptionQuantity(
                id,
                PageRequest.of(pageNumber, pageSize, Sort.by(direction, "consumptionsQuantity"))
        );
    }

    public ConsumedMealsNumberAndDailyAverage getConsumedMealsNumberAndDailyAverage(long id) {
        return mealRepository.countConsumedMealsNumberAndDailyAverage(id);
    }

    public List<MealsPerDifficultyNumber> getMealsPerDifficultyNumber(long id) {
        return mealRepository.countMealsPerDifficultyNumber(id);
    }

    public List<MealsPerCategoryNumber> getMealsPerCategoryNumber(long id) {
        return mealRepository.countMealsPerCategoryNumber(id);
    }

    public List<MealsConsumedCalories> getMealsConsumedCalories(long id,
                                                                int pageNumber,
                                                                int pageSize,
                                                                Sort.Direction direction) {
        return mealRepository.countMealsConsumedCalories(id,
                PageRequest.of(pageNumber, pageSize, Sort.by(direction, "caloriesSum"))
        );
    }

    public TimeSpentCookingSum getTimeSpentCookingSum(long id) {
        return mealRepository.countTimeSpentCookingSum(id);
    }

    public ConsumedCaloriesSumWithDailyAverage getConsumedCaloriesSumWithDailyAverage(long id) {
        return mealRepository.countConsumedCaloriesSumWithDailyAverage(id);
    }

    public ConsumedGramsSumWithDailyAverage getConsumedGramsSumWithDailyAverage(long id) {
        return mealRepository.countConsumedGramsSumWithDailyAverage(id).orElseThrow();
    }

    public List<DailyConsumedCalories> getDailyConsumedCalories(long id, int pageNumber, int pageSize) {
        return mealRepository.countDailyConsumedCalories(id,
                PageRequest.of(pageNumber, pageSize)
        );
    }

    public List<DailyConsumedGrams> getDailyConsumedGrams(long id, int pageNumber, int pageSize) {
        return mealRepository.countDailyConsumedGrams(id,
                PageRequest.of(pageNumber, pageSize)
        );
    }

    public List<DailyCookingTime> getDailyCookingTime(long id, int pageNumber, int pageSize) {
        return mealRepository.countDailyCookingTime(id,
                PageRequest.of(pageNumber, pageSize)
        );
    }

}
