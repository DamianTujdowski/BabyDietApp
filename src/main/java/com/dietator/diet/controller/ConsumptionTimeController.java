package com.dietator.diet.controller;

import com.dietator.diet.domain.ConsumptionTime;
import com.dietator.diet.service.ConsumptionTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConsumptionTimeController {

    private final ConsumptionTimeService consumptionTimeService;

    @GetMapping(value = "/consumptionTimes/{id}")
    public ConsumptionTime getConsumptionTime(@PathVariable long id) {
        return consumptionTimeService.findConsumptionTimeById(id);
    }

    @GetMapping("/consumptionTimes")
    public List<ConsumptionTime> getAllConsumptionTimes() {
        return consumptionTimeService.findAll();
    }

    @PostMapping("/consumptionTimes")
    public ConsumptionTime save(@RequestBody ConsumptionTime consumptionTime) {
        return consumptionTimeService.save(consumptionTime);
    }
}
