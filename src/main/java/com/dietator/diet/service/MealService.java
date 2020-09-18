package com.dietator.diet.service;

import com.dietator.diet.domain.ConsumptionTime;
import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MealService {

    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

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
        editedMeal.getConsumptionTime().addAll(Objects.requireNonNull(meal.getConsumptionTime()));
        editedMeal.getIngredients()
                .addAll(Objects.requireNonNull(copyPreDefinedIngredients(meal.getIngredients(), editedMeal.getIngredients())));
        editedMeal.setMealCategory(meal.getMealCategory());
        editedMeal.setPreparationDifficulty(meal.getPreparationDifficulty());
        return editedMeal;
    }

    public void deleteMeal(long id) {
        mealRepository.deleteById(id);
    }

    private Set<Ingredient> copyPreDefinedIngredients(Set<Ingredient> clientIngredients, Set<Ingredient> dbIngredients) {
        return removeCommonIngredients(clientIngredients, dbIngredients)
                .stream()
                .map(this::copyAndSaveIngredient)
                .collect(Collectors.toSet());
    }

    private Set<Ingredient> removeCommonIngredients(Set<Ingredient> clientIngredients, Set<Ingredient> dbIngredients) {
        Set<Ingredient> newIngredients = new HashSet<>(clientIngredients);
        newIngredients.removeAll(dbIngredients);
        return newIngredients;
    }

    private Ingredient copyAndSaveIngredient(Ingredient ingredient) {
        return ingredient.isPreDefined() ? ingredientRepository.save(new Ingredient(ingredient)) : ingredient;
    }

}
