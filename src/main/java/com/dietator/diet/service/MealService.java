package com.dietator.diet.service;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static com.dietator.diet.utils.ConsumptionTimePersistenceUtils.filterNewConsumptionTimes;
import static com.dietator.diet.utils.IngredientPersistenceUtils.clonePreDefinedIngredients;
import static com.dietator.diet.utils.IngredientPersistenceUtils.filterNewIngredients;

@RequiredArgsConstructor
@Service
public class MealService {

    private final MealRepository mealRepository;

    public Meal findMealById(long id) {
        return mealRepository.findById(id).orElseThrow();
    }

    public List<MealInfo> findAllMeals() {
        return mealRepository.findAllBy();
    }

    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    @Transactional
    public Meal editMeal(Meal meal) {
        Meal editedMeal = mealRepository.findById(meal.getId()).orElseThrow();
        editedMeal.setDesignation(meal.getDesignation());
        editedMeal.setEnergy(meal.getEnergy());
        editedMeal.setPreparationDescription(meal.getPreparationDescription());
        editedMeal.setPreparationDuration(meal.getPreparationDuration());
        editedMeal.getConsumptionTime()
                .addAll(Objects.requireNonNull(filterNewConsumptionTimes(meal.getConsumptionTime(), editedMeal.getConsumptionTime())));
        editedMeal.getIngredients()
                .addAll(Objects.requireNonNull(clonePreDefinedIngredients(filterNewIngredients(meal.getIngredients(), editedMeal.getIngredients()))));
        editedMeal.setMealCategory(meal.getMealCategory());
        editedMeal.setPreparationDifficulty(meal.getPreparationDifficulty());
        return editedMeal;
    }

    public void deleteMeal(long id) {
        mealRepository.deleteById(id);
    }
}
