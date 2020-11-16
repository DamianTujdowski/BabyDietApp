package com.dietator.diet.controller;

import com.dietator.diet.projections.statistics_projections.*;
import com.dietator.diet.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/stats/meals/")
    public List<MealsConsumptionQuantity> getMealsConsumptionQuantity(@RequestParam long id,
                                                                      @RequestParam int page,
                                                                      @RequestParam(defaultValue = "10") int pageSize,
                                                                      @RequestParam Sort.Direction direction) {
        return statisticsService.getMealsConsumptionQuantity(id, page, pageSize, direction);
    }

    @GetMapping("/stats/meals-number/")
    public ConsumedMealsNumberAndDailyAverage getConsumedMealsNumberAndDailyAverage(@RequestParam long id) {
        return statisticsService.getConsumedMealsNumberAndDailyAverage(id);
    }

    @GetMapping("/stats/meals/difficulty/")
    public List<MealsPerDifficultyNumber> getMealsPerDifficultyNumber(@RequestParam long id) {
        return statisticsService.getMealsPerDifficultyNumber(id);
    }

    @GetMapping("/stats/meals/category/")
    public List<MealsPerCategoryNumber> getMealsPerCategoryNumber(@RequestParam long id) {
        return statisticsService.getMealsPerCategoryNumber(id);
    }

    @GetMapping("/stats/calories/")
    public List<MealsConsumedCalories> getMealsConsumedCalories(@RequestParam long id,
                                                                @RequestParam int page,
                                                                @RequestParam(defaultValue = "10") int pageSize,
                                                                @RequestParam Sort.Direction direction) {
        return statisticsService.getMealsConsumedCalories(id, page, pageSize, direction);
    }

    @GetMapping("/stats/calories/sum")
    public ConsumedCaloriesSumWithDailyAverage getConsumedCaloriesSumWithDailyAverage(@RequestParam long id) {
        return statisticsService.getConsumedCaloriesSumWithDailyAverage(id);
    }


    @GetMapping("/stats/time/")
    public TimeSpentCookingSum getTimeSpentCookingSum(@RequestParam long id) {
        return statisticsService.getTimeSpentCookingSum(id);
    }

    @GetMapping("/stats/grams/")
    public ConsumedGramsSumWithDailyAverage getConsumedGramsSumWithDailyAverage(@RequestParam long id) {
        return statisticsService.getConsumedGramsSumWithDailyAverage(id);
    }


}
