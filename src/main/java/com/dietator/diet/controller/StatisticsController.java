package com.dietator.diet.controller;

import com.dietator.diet.projections.statistics_projections.MealsConsumptionQuantity;
import com.dietator.diet.projections.statistics_projections.AllConsumedMealsNumber;
import com.dietator.diet.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/stats/meals/{id}")
    public List<MealsConsumptionQuantity> getMealsConsumptionQuantity(@PathVariable long id) {
        return statisticsService.getMealsConsumptionQuantity(id);
    }

    @GetMapping("/stats/meals-number/{id}")
    public AllConsumedMealsNumber getAllConsumedMealsNumber(@PathVariable long id) {
        return statisticsService.getAllConsumedMealsNumber(id);
    }
}
