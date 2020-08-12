package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.ChildInfo;
import com.dietator.diet.repository.ChildRepository;
import com.dietator.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final MealRepository mealRepository;

    public Child getChildById(int id) {
        return childRepository.findById(id).orElseThrow();
    }

    //TODO make favourite and dislike ingredients display pairs ingredient name + ingredient favourite or dislike only info

    public List<ChildInfo> findAllChildren() {
        return childRepository.findAllBy();
    }

    @Transactional
    public Child saveChild(Child child) {
        Set<Meal> childMeals = child.getConsumedMeals();
        childMeals.forEach(this::cloneMeal);
// test if is working
        child.setConsumedMeals(childMeals);
        return childRepository.save(child);
    }

    @Transactional
    public Child editChild(Child child) {
        Child editedChild = childRepository.findById(child.getId()).orElseThrow();
        editedChild.setFirstName(child.getFirstName());
        editedChild.setBirthDate(child.getBirthDate());
        editedChild.setConsumedMeals(child.getConsumedMeals());
        editedChild.setFavouriteAndDislikedIngredients(child.getFavouriteAndDislikedIngredients());
        return editedChild;
    }

    public void deleteChild(int id) {
        childRepository.deleteById(id);
    }

    private void cloneMeal(Meal meal) {
        if (meal.getEnergy() > 200){
            Meal clonedMeal = new Meal();
            BeanUtils.copyProperties(meal, clonedMeal, "id");
//            clonedMeal.setId(null);
            mealRepository.save(clonedMeal);
        }
    }
}
