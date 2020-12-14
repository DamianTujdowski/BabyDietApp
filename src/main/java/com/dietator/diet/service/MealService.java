package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.error.ParentEntityNotFoundException;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.repository.ChildRepository;
import com.dietator.diet.repository.MealRepository;
import com.dietator.diet.utils.MealEnergyValueUpdater;
import com.dietator.diet.utils.PredefinedIngredientCopier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class MealService {

    private final MealRepository mealRepository;

    private final PredefinedIngredientCopier predefinedIngredientCopier;

    private final MealEnergyValueUpdater energyValueUpdater;

    private final ChildRepository childRepository;

    public Meal findMealById(long id) {
        return mealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Meal.class, id));
    }

    public List<MealInfo> findAllMeals(long id, int pageNumber, int pageSize) {
        checkIfParentEntityExists(id);
        return mealRepository.findAllByParentId(id, PageRequest.of(pageNumber, pageSize));
    }

    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    @Transactional
    public Meal editMeal(Meal meal) {
        Meal editedMeal = mealRepository.findById(meal.getId())
                .orElseThrow(() -> new EntityNotFoundException(Meal.class, meal.getId()));
        editedMeal.setDesignation(meal.getDesignation());
        editedMeal.setEnergy(energyValueUpdater.countMealEnergyValue(meal, editedMeal));
        editedMeal.setPreparationDescription(meal.getPreparationDescription());
        editedMeal.setPreparationDuration(meal.getPreparationDuration());
        editedMeal.getConsumptionTimes().addAll(requireNonNull(meal.getConsumptionTimes()));
        editedMeal.getIngredients()
                .addAll(requireNonNull(predefinedIngredientCopier.copyPreDefinedIngredients(meal.getIngredients(),
                        editedMeal.getIngredients()))
                );
        editedMeal.setMealCategory(meal.getMealCategory());
        editedMeal.setPreparationDifficulty(meal.getPreparationDifficulty());
        return editedMeal;
    }

    public void deleteMeal(long id) {
        checkIfExists(id);
        mealRepository.deleteById(id);
    }

    private void checkIfExists(long id) {
        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException(Meal.class, id);
        }
    }

    private void checkIfParentEntityExists(long id) {
        if (!childRepository.existsById(id)) {
            throw new ParentEntityNotFoundException(Child.class, Meal.class, id);
        }
    }
}
