package com.dietator.diet.controller;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<MealInfo> getAllMeals(@RequestParam long id,
                                      @RequestParam int pageNumber,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        return mealService.findAllMeals(id, pageNumber, pageSize);
    }

    @GetMapping("/meals/predefined")
    public List<MealInfo> getPreDefinedMeals(@RequestParam int pageNumber,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        return mealService.findAllPredefined(pageNumber, pageSize);
    }

    @GetMapping("/meals/preposition/{id}")
    public List<MealInfo> getProbablyLikedMeals(Long id) {
        return mealService.getProbablyLikedMeals(id);
    }


    @PostMapping("/meals")
    public Meal saveMeal(@Valid @RequestBody Meal meal) {
        return mealService.save(meal);
    }

    @PutMapping("/meals")
    public Meal editMeal(@Valid @RequestBody Meal meal) {
        return mealService.editMeal(meal);
    }

    @DeleteMapping("/meals/{id}")
    public void deleteMeal(@PathVariable long id) {
        mealService.deleteMeal(id);
    }
}
