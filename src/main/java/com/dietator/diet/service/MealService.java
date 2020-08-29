package com.dietator.diet.service;

import com.dietator.diet.domain.ConsumptionTime;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    //TODO refactor adding new objects to collections
    @Transactional
    public Meal editMeal(Meal meal) {
        Meal editedMeal = mealRepository.findById(meal.getId()).orElseThrow();
        editedMeal.setDesignation(meal.getDesignation());
        editedMeal.setEnergy(meal.getEnergy());
        editedMeal.setPreparationDescription(meal.getPreparationDescription());
        editedMeal.setPreparationDuration(meal.getPreparationDuration());
        editedMeal.getConsumptionTime().addAll(Objects.requireNonNull(getNewConsumptionTimes(meal.getConsumptionTime(), editedMeal.getConsumptionTime())));
        editedMeal.setIngredients(meal.getIngredients());
        editedMeal.setMealCategory(meal.getMealCategory());
        editedMeal.setPreparationDifficulty(meal.getPreparationDifficulty());
        editedMeal.setPrePrepared(meal.isPrePrepared());
        return editedMeal;
    }

    private Set<ConsumptionTime> getNewConsumptionTimes(Set<ConsumptionTime> consumptionTimesFromUser, Set<ConsumptionTime> consumptionTimesFromDb) {
        consumptionTimesFromUser.removeAll(consumptionTimesFromDb);
        return consumptionTimesFromUser;
    }

    public void deleteMeal(long id) {
        mealRepository.deleteById(id);
    }
}
