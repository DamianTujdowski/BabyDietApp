package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.ChildInfo;
import com.dietator.diet.repository.ChildRepository;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    public Child getChildById(long id) {
        return childRepository.findById(id).orElseThrow();
    }

    //TODO make favourite and dislike ingredients display pairs ingredient name + ingredient favourite or dislike only info
    public List<ChildInfo> findAllChildren() {
        return childRepository.findAllBy();
    }

    public Child saveChild(Child child) {
        return childRepository.save(child);
    }

    //TODO refactor cloning meals - add new method instead of iterating over meals set
    @Transactional
    public Child editChild(Child child) {
        Child editedChild = childRepository.findById(child.getId()).orElseThrow();
        editedChild.setFirstName(child.getFirstName());
        editedChild.setBirthDate(child.getBirthDate());
        editedChild.setConsumedMeals(clonePrePreparedMeals(child.getConsumedMeals()));
        editedChild.setFavouriteAndDislikedIngredients(child.getFavouriteAndDislikedIngredients());
        return editedChild;
    }

    public void deleteChild(long id) {
        childRepository.deleteById(id);
    }

    private Set<Meal> clonePrePreparedMeals(Set<Meal> consumedMeals) {
        return consumedMeals
                .stream()
                .map(this::cloneAndSave)
                .collect(Collectors.toSet());
    }

    private Meal cloneAndSave(Meal meal) {
        Meal clonedMeal = new Meal(meal);
        clonedMeal.getIngredients().forEach(ingredientRepository::save);
        return meal.isPrePrepared() ? mealRepository.save(clonedMeal) : meal;
    }
}
