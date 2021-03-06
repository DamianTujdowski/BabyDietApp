package com.dietator.diet.controller;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.projections.IngredientBasicInfo;
import com.dietator.diet.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/ingredients/{id}")
    public Ingredient getIngredient(@PathVariable long id) {
        return ingredientService.findIngredientById(id);
    }

    @GetMapping("/ingredients")
    public List<IngredientBasicInfo> getPreDefinedInredients(@RequestParam int pageNumber,
                                                             @RequestParam(defaultValue = "10") int pageSize) {
        return ingredientService.findAll(pageNumber, pageSize);
    }

    @PostMapping("/ingredients")
    public Ingredient saveIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ingredientService.save(ingredient);
    }

    @PutMapping("/ingredients")
    public Ingredient editIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ingredientService.edit(ingredient);
    }

    @DeleteMapping("/ingredients/{id}")
    public void deleteIngredient(@PathVariable long id) {
        ingredientService.delete(id);
    }

}
