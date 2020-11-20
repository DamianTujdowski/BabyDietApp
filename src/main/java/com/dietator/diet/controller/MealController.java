package com.dietator.diet.controller;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MealController {

    private final MealService mealService;

    @GetMapping("/meals/{id}")
    public Meal getMeal(@PathVariable long id) {
        return mealService.findMealById(id);
    }

    @GetMapping("/meals")
    public List<MealInfo> getAllMeals(@RequestParam long id) {
        return mealService.findAllMeals(id);
    }

    @PostMapping("/meals")
    public Meal saveMeal(@RequestBody Meal meal) {
        return mealService.save(meal);
    }

    @PutMapping("/meals")
    public Meal editMeal(@RequestBody Meal meal) {
        return mealService.editMeal(meal);
    }

    @DeleteMapping("/meals/{id}")
    public void deleteMeal(@PathVariable long id) {
        mealService.deleteMeal(id);
    }
}
