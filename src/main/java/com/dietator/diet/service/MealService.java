package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.error.ParentEntityNotFoundException;
import com.dietator.diet.projections.IngredientBasicInfo;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.repository.ChildRepository;
import com.dietator.diet.repository.IngredientRepository;
import com.dietator.diet.repository.MealRepository;
import com.dietator.diet.utils.MealEnergyValueUpdater;
import com.dietator.diet.utils.PredefinedIngredientCopier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class MealService {

    private final MealRepository mealRepository;

    private final PredefinedIngredientCopier predefinedIngredientCopier;

    private final MealEnergyValueUpdater energyValueUpdater;

    private final ChildRepository childRepository;

    private final IngredientRepository ingredientRepository;

    public Meal findMealById(long id) {
        return mealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Meal.class, id));
    }

    public List<MealInfo> findAllMeals(long id, int pageNumber, int pageSize) {
        checkIfParentEntityExists(id);
        return mealRepository.findAllByParentId(id, PageRequest.of(pageNumber, pageSize));
    }

    public List<MealInfo> findAllPredefined(int pageNumber, int pageSize) {
        return mealRepository.findByIsPreDefinedTrue(PageRequest.of(pageNumber, pageSize));
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

    public List<MealInfo> getProbablyLikedMeals(Long id) {
        checkIfParentEntityExists(id);

        List<MealInfo> predefinedMeals = mealRepository.findByIsPreDefinedTrue();

        List<IngredientBasicInfo> dislikedIngredients = ingredientRepository.findByIsDislikedTrueAndChildId(id);
        List<IngredientBasicInfo> favouriteIngredients = ingredientRepository.findByIsFavouriteTrueAndChildId(id);

        List<String> favouriteIngredientsDesignations = getDesignations(favouriteIngredients);
        List<String> dislikedIngredientsDesignations = getDesignations(dislikedIngredients);

        return predefinedMeals
                .stream()
                .filter(meal -> checkForCommonIngredients(meal, favouriteIngredientsDesignations))
                .filter(meal -> !checkForCommonIngredients(meal, dislikedIngredientsDesignations))
                .sorted(Comparator
                        .comparingLong(meal -> filterFavouriteIngredients((MealInfo) meal, favouriteIngredientsDesignations))
                        .reversed())
                .collect(Collectors.toList());
    }

    private long filterFavouriteIngredients(MealInfo meal, List<String> favouriteIngredients) {
        return meal
                .getIngredients()
                .stream()
                .filter(ingredient -> favouriteIngredients.contains(ingredient.getDesignation()))
                .count();
    }

    private List<String> getDesignations(List<IngredientBasicInfo> favouriteIngredients) {
        return favouriteIngredients
                .stream()
                .map(IngredientBasicInfo::getDesignation)
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean checkForCommonIngredients(MealInfo meal, List<String> ingredientDesignation) {
        return meal
                .getIngredients()
                .stream()
                .map(IngredientBasicInfo::getDesignation)
                .anyMatch(ingredientDesignation::contains);
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
