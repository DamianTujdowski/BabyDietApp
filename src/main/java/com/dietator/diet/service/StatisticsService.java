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

    //TODO repair error while there is no id in DB

    public List<MealsConsumptionQuantity> getMealsConsumptionQuantity(long id,
                                                                      int pageNumber,
                                                                      int pageSize,
                                                                      Sort.Direction direction) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }

        return mealRepository.countMealsConsumptionQuantity(
                id,
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(direction, "consumptionsQuantity")
                ));
    }

    public ConsumedMealsNumberAndDailyAverage getConsumedMealsQuantityWithDailyAverage(long id) {
        return mealRepository.countConsumedMealsNumberAndDailyAverage(id);
    }

    public List<MealsPerDifficultyNumber> getMealsPerDifficultyQuantity(long id) {
        return mealRepository.countMealsPerDifficultyNumber(id);
    }

    public List<MealsPerCategoryNumber> getMealsPerCategoryQuantity(long id) {
        return mealRepository.countMealsPerCategoryNumber(id);
    }

    public List<MealsConsumedCalories> getMealsConsumedCaloriesSum(long id,
                                                                   int pageNumber,
                                                                   int pageSize,
                                                                   Sort.Direction direction) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }

        return mealRepository.countMealsConsumedCalories(
                id,
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(direction, "caloriesSum")
                ));
    }

    public List<DailyConsumedCalories> getDailyConsumedCalories(long id, int pageNumber, int pageSize) {

        if (id < 0) {
            throw new IllegalArgumentException();
        }

        return mealRepository.countDailyConsumedCalories(
                id, PageRequest.of(pageNumber, pageSize));
    }

    public ConsumedCaloriesSumWithDailyAverage getConsumedCaloriesSumWithDailyAverage(long id) {
        return mealRepository.countConsumedCaloriesSumWithDailyAverage(id);
    }

    public List<MealsConsumedGrams> getMealsConsumedGramsSum(long id,
                                                             int pageNumber,
                                                             int pageSize,
                                                             Sort.Direction direction) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }

        return mealRepository.countMealsConsumedGrams(
                id,
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(direction, "gramsSum")
                ));
    }

    public ConsumedGramsSumWithDailyAverage getConsumedGramsSumWithDailyAverage(long id) {
        return mealRepository.countConsumedGramsSumWithDailyAverage(id);
    }

    public List<DailyConsumedGrams> getDailyConsumedGrams(long id, int pageNumber, int pageSize) {

        if (id < 0) {
            throw new IllegalArgumentException();
        }

        return mealRepository.countDailyConsumedGrams(
                id, PageRequest.of(pageNumber, pageSize));
    }

    public List<DailyCookingTime> getDailyCookingTime(long id, int pageNumber, int pageSize) {

        if (id < 0) {
            throw new IllegalArgumentException();
        }

        return mealRepository.countDailyCookingTime(
                id, PageRequest.of(pageNumber, pageSize));
    }

    public TimeSpentCookingSum getTimeSpentCookingSum(long id) {
        return mealRepository.countTimeSpentCookingSum(id);
    }


}
