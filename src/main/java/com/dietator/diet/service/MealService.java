package com.dietator.diet.service;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.repository.MealRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
@Service
public class MealService {

    private MealRepository mealRepository;

    public Set<Meal> findAllMeals() {
        return mealRepository.findAllMeals();
    }

    public Meal getMealById(int id) {
        return mealRepository.findById(id).orElseThrow();
    }

    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    @Transactional
    public Meal editMeal(Meal meal) {
        Meal editedMeal = mealRepository.findById(meal.getId()).orElseThrow();
        editedMeal.setDesignation(meal.getDesignation());
        editedMeal.setWeight(meal.getWeight());
        editedMeal.setKcal(meal.getKcal());
        editedMeal.setConsumptionTime(meal.getConsumptionTime());
        return editedMeal;
    }

    public void deleteMeal(int id) {
        mealRepository.deleteById(id);
    }
}
