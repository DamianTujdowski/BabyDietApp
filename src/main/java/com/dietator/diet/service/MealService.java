package com.dietator.diet.service;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MealService {

    private final MealRepository mealRepository;

    public Meal getMealById(int id) {
        return mealRepository.findById(id).orElseThrow();
    }

    public List<Meal> findAllMeals() {
        return mealRepository.findAll();
    }

    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    //TODO refactor adding new ingredient
    @Transactional
    public Meal editMeal(Meal meal) {
        Meal editedMeal = mealRepository.findById(meal.getId()).orElseThrow();
        editedMeal.setDesignation(meal.getDesignation());
        editedMeal.setEnergy(meal.getEnergy());
        editedMeal.setPreparationDescription(meal.getPreparationDescription());
        editedMeal.setPreparationDuration(meal.getPreparationDuration());
        editedMeal.setConsumptionTime(meal.getConsumptionTime());
        editedMeal.setIngredients(meal.getIngredients());
        editedMeal.setMealCategory(meal.getMealCategory());
        editedMeal.setPreparationDifficulty(meal.getPreparationDifficulty());
        return editedMeal;
    }

    public void deleteMeal(int id) {
        mealRepository.deleteById(id);
    }
}
