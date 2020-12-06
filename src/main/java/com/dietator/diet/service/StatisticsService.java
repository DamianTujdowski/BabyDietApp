package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.error.ParentEntityNotFoundException;
import com.dietator.diet.projections.statistics_projections.*;
import com.dietator.diet.repository.ChildRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final MealRepository mealRepository;
    private final ChildRepository childRepository;

    public List<MealsConsumptionQuantity> getMealsConsumptionQuantity(long id,
                                                                      int pageNumber,
                                                                      int pageSize,
                                                                      Sort.Direction direction) {
        checkIfExists(id);
        checkPageNumber(id);

        return mealRepository.countMealsConsumptionQuantity(
                id,
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(direction, "consumptionsQuantity")
                ));
    }

    public ConsumedMealsNumberAndDailyAverage getConsumedMealsQuantityWithDailyAverage(long id) {
        checkIfExists(id);
        return mealRepository.countConsumedMealsNumberAndDailyAverage(id);
    }

    public List<MealsPerDifficultyNumber> getMealsPerDifficultyQuantity(long id) {
        checkIfExists(id);
        return mealRepository.countMealsPerDifficultyNumber(id);
    }

    public List<MealsPerCategoryNumber> getMealsPerCategoryQuantity(long id) {
        checkIfExists(id);
        return mealRepository.countMealsPerCategoryNumber(id);
    }

    public List<MealsConsumedCalories> getMealsConsumedCaloriesSum(long id,
                                                                   int pageNumber,
                                                                   int pageSize,
                                                                   Sort.Direction direction) {
        checkIfExists(id);
        checkPageNumber(id);

        return mealRepository.countMealsConsumedCalories(
                id,
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(direction, "caloriesSum")
                ));
    }

    public List<DailyConsumedCalories> getDailyConsumedCalories(long id, int pageNumber, int pageSize) {

        checkPageNumber(id);

        return mealRepository.countDailyConsumedCalories(
                id, PageRequest.of(pageNumber, pageSize));
    }

    public ConsumedCaloriesSumWithDailyAverage getConsumedCaloriesSumWithDailyAverage(long id) {
        checkIfExists(id);
        return mealRepository.countConsumedCaloriesSumWithDailyAverage(id);
    }

    public List<MealsConsumedGrams> getMealsConsumedGramsSum(long id,
                                                             int pageNumber,
                                                             int pageSize,
                                                             Sort.Direction direction) {
        checkIfExists(id);
        checkPageNumber(id);

        return mealRepository.countMealsConsumedGrams(
                id,
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(direction, "gramsSum")
                ));
    }

    public ConsumedGramsSumWithDailyAverage getConsumedGramsSumWithDailyAverage(long id) {
        checkIfExists(id);
        return mealRepository.countConsumedGramsSumWithDailyAverage(id);
    }

    public List<DailyConsumedGrams> getDailyConsumedGrams(long id,
                                                          int pageNumber,
                                                          int pageSize) {
        checkIfExists(id);
        checkPageNumber(id);

        return mealRepository.countDailyConsumedGrams(
                id, PageRequest.of(pageNumber, pageSize));
    }

    public List<DailyCookingTime> getDailyCookingTime(long id,
                                                      int pageNumber,
                                                      int pageSize) {
        checkIfExists(id);
        checkPageNumber(id);

        return mealRepository.countDailyCookingTime(
                id, PageRequest.of(pageNumber, pageSize));
    }

    public TimeSpentCookingSum getTimeSpentCookingSum(long id) {
        checkIfExists(id);
        return mealRepository.countTimeSpentCookingSum(id);
    }

    private void checkIfExists(long id) {
        if (!childRepository.existsById(id)) {
            throw new ParentEntityNotFoundException(Child.class, Meal.class, id);
        }
    }

    private void checkPageNumber(long id) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
    }

}
