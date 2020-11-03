package com.dietator.diet.controller;

import com.dietator.diet.projections.MealConsumptionsCount;
import com.dietator.diet.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/stats")
    public List<MealConsumptionsCount> getMealConsumptionsCount() {
        return statisticsService.getMealConsumptionsCount();
    }
}
