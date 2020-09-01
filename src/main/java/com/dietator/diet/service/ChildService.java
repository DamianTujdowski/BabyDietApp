package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.ChildInfo;
import com.dietator.diet.repository.ChildRepository;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    public Child findById(long id) {
        return childRepository.findById(id).orElseThrow();
    }

    //TODO make favourite and dislike ingredients display pairs ingredient name + ingredient favourite or dislike only info
    public List<ChildInfo> findAllChildren() {
        return childRepository.findAllBy();
    }

    public Child saveChild(Child child) {
        return childRepository.save(child);
    }

    @Transactional
    public Child editChild(Child child) {
        Child editedChild = childRepository.findById(child.getId()).orElseThrow();
        editedChild.setFirstName(child.getFirstName());
        editedChild.setBirthDate(child.getBirthDate());
        editedChild.getConsumedMeals()
                .addAll(Objects.requireNonNull(clonePreDefinedMeals(filterNewMeals(child.getConsumedMeals(), editedChild.getConsumedMeals()))));
        editedChild.getFavouriteAndDislikedIngredients()
                .addAll(Objects.requireNonNull(clonePreDefinedIngredients(filterNewIngredients(child.getFavouriteAndDislikedIngredients(), editedChild.getFavouriteAndDislikedIngredients()))));
        return editedChild;
    }

    public void deleteChild(long id) {
        childRepository.deleteById(id);
    }

    private Set<Ingredient> filterNewIngredients(Set<Ingredient> ingredientsFromUser, Set<Ingredient> ingredientsFromDb) {
        ingredientsFromUser.removeAll(ingredientsFromDb);
        return ingredientsFromUser;
    }

    private Set<Meal> filterNewMeals(Set<Meal> consumedMealsFromUser, Set<Meal> consumedMealsFromDb) {
        consumedMealsFromUser.removeAll(consumedMealsFromDb);
        return consumedMealsFromUser;
    }

    private Set<Ingredient> clonePreDefinedIngredients(Set<Ingredient> favouriteAndDislikedIngredients) {
        return favouriteAndDislikedIngredients
                .stream()
                .map(this::cloneAndSaveIngredient)
                .collect(Collectors.toSet());
    }

    private Ingredient cloneAndSaveIngredient(Ingredient ingredient) {
        return ingredient.isPreDefined() ? ingredientRepository.save(new Ingredient(ingredient)) : ingredient;
    }

    private Set<Meal> clonePreDefinedMeals(Set<Meal> consumedMeals) {
        return consumedMeals
                .stream()
                .map(this::cloneAndSaveMeal)
                .collect(Collectors.toSet());
    }

    private Meal cloneAndSaveMeal(Meal meal) {
        Meal clonedMeal = new Meal(meal);
        if (meal.isPreDefined()) {
            clonedMeal.getIngredients().forEach(ingredientRepository::save);
            return mealRepository.save(clonedMeal);
        } else {
            return meal;
        }
    }
}
