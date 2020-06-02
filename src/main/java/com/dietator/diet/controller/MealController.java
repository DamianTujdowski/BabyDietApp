package com.dietator.diet.controller;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.repository.MealRepository;
import org.springframework.web.bind.annotation.*;

@RestController
class MealController {

    private MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @PostMapping("/meals")
    public void addMeal(@RequestBody Meal meal) {
        mealRepository.save(meal);
    }

    @GetMapping("/meals")
    public Iterable<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    @GetMapping("/meals/{id}")
    public Meal showMeal(@PathVariable int id) {
        return mealRepository.findById(id)
                .orElseThrow();
    }
}
