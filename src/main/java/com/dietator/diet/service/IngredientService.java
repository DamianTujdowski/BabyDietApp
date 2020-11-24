package com.dietator.diet.service;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.projections.IngredientBasicInfo;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private final MealRepository mealRepository;

    public Ingredient findIngredientById(long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Ingredient.class, id));
    }

    public List<IngredientBasicInfo> findAll() {
        return ingredientRepository.findAllBy();
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Transactional
    public Ingredient edit(Ingredient ingredient) {
        Ingredient editedIngredient = ingredientRepository.findById(ingredient.getId())
                .orElseThrow(() -> new EntityNotFoundException(Ingredient.class, ingredient.getId()));
        editedIngredient.setDesignation(ingredient.getDesignation());
        editedIngredient.setEnergyPer100Grams(ingredient.getEnergyPer100Grams());
        editedIngredient.setFavourite(ingredient.isFavourite());
        editedIngredient.setDisliked(ingredient.isDisliked());
        updateMealEnergyValue(editedIngredient.getId());
        return editedIngredient;
    }

    @Transactional
    public void delete(long id) {
        updateMealEnergyValue(id);
        ingredientRepository.deleteById(id);
    }

    private void updateMealEnergyValueEditedIng(long id) {
        Ingredient edited = findIngredientById(id);
        Meal meal = findMealById(edited.getMealId());
        Set<Ingredient> ingredients = meal.getIngredients();
        updateIngredients(ingredients, edited);
        meal.setEnergy(countMealEnergy(ingredients));
    }

    private void updateIngredients(Set<Ingredient> ingredients, Ingredient edited) {
        ingredients.forEach(ingredient -> {
            if (ingredient.getId() == edited.getId()) {
                ingredient.setEnergyPer100Grams(edited.getEnergyPer100Grams());
            }
        });
    }

    private void updateMealEnergyValue(long id) {
        Ingredient toDelete = findIngredientById(id);
        Meal meal = findMealById(toDelete.getMealId());
        Set<Ingredient> ingredients = meal.getIngredients();
        ingredients.remove(toDelete);
        meal.setEnergy(countMealEnergy(ingredients));
    }

    private Meal findMealById(long id) {
        return mealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Meal.class, id));
    }

    private int countMealEnergy(Set<Ingredient> ingredients) {
        return ingredients
                .stream()
                .mapToInt(this::countEnergyPerMeal)
                .sum();
    }

    private int countEnergyPerMeal(Ingredient ingredient) {
        return (int) Math.round(ingredient.getWeightPerMeal() / 100.0 * ingredient.getEnergyPer100Grams());
    }
}
