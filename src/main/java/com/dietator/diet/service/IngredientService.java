package com.dietator.diet.service;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.projections.IngredientBasicInfo;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.utils.MealEnergyValueUpdater;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final MealEnergyValueUpdater mealEnergyUpdater;

    public Ingredient findIngredientById(long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Ingredient.class, id));
    }

    public List<IngredientBasicInfo> findAll(int pageNumber, int pageSize) {
        return ingredientRepository.findByIsPreDefinedTrue(PageRequest.of(pageNumber, pageSize));
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
        mealEnergyUpdater.updateMealEnergyValue(editedIngredient);
        return editedIngredient;
    }

    @Transactional
    public void delete(long id) {
        mealEnergyUpdater.updateMealEnergyValue(id);
        ingredientRepository.deleteById(id);
    }

}
