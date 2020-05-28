package com.dietator.diet.controller;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.repository.MealRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MealController {

    private MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @PostMapping("/addMeal")
    public void addMeal(@RequestBody Meal meal) {
        mealRepository.save(meal);
    }

    @GetMapping("/allMeals")
    public Iterable<Meal> getAllMeals() {
        return mealRepository.findAll();
    }
}
