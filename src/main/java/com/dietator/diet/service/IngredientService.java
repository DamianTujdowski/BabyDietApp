package com.dietator.diet.service;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.projections.IngredientBasicInfo;
import com.dietator.diet.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

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
        return editedIngredient;
    }

    public void delete(long id) {
        ingredientRepository.deleteById(id);
    }
}
