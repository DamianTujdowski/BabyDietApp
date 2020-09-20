package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.projections.ChildInfo;
import com.dietator.diet.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;

    private final PredefinedMealCopyingService mealCopyingService;

    private final PredefinedIngredientCopyingService predefinedIngredientCopyingService;

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
                .addAll(Objects.requireNonNull(mealCopyingService.copyPreDefinedMeals(child.getConsumedMeals(), editedChild.getConsumedMeals())));
        editedChild.getFavouriteAndDislikedIngredients()
                .addAll(Objects.requireNonNull(predefinedIngredientCopyingService.copyPreDefinedIngredients(child.getFavouriteAndDislikedIngredients(),
                        editedChild.getFavouriteAndDislikedIngredients())));
        return editedChild;
    }

    public void deleteChild(long id) {
        childRepository.deleteById(id);
    }

}
