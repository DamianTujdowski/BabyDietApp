package com.dietator.diet.controller;

import com.dietator.diet.projections.statistics_projections.MealsConsumptionQuantity;
import com.dietator.diet.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                                                                      @RequestParam(defaultValue = "10") int pageSize) {
        return statisticsService.getMealsConsumptionQuantity(id, page, pageSize);
    }

    @GetMapping("/stats/meals-number/{id}")
    public long getAllConsumedMealsNumber(@PathVariable long id) {
        return statisticsService.getAllConsumedMealsNumber(id);
    }
}
