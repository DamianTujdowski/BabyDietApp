package com.dietator.diet.controller;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.projections.IngredientBasicInfo;
import com.dietator.diet.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/ingredients/{id}")
    public Ingredient getIngredient(@PathVariable int id) {
        return ingredientService.findIngredientById(id);
    }

    @GetMapping("/ingredients")
    public List<IngredientBasicInfo> getAllInredients() {
        return ingredientService.findAll();
    }

    @PostMapping("/ingredients")
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.save(ingredient);
    }

    @PutMapping("/ingredients")
    public Ingredient editIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.edit(ingredient);
    }

    @DeleteMapping("/ingredients/{id}")
    public void deleteIngredient(@PathVariable int id) {
        ingredientService.delete(id);
    }

}
