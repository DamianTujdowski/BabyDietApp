package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.projections.ChildInfo;
import com.dietator.diet.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.requireNonNull;


@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;

    private final PredefinedMealCopyingService mealCopyingService;

    private final PredefinedIngredientCopyingService predefinedIngredientCopyingService;

    public Child findById(long id) {
        return childRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Child.class, id));
    }

    public List<ChildInfo> findAllChildren() {
        return childRepository.findAllBy();
    }

    public Child saveChild(Child child) {
        return childRepository.save(child);
    }

    @Transactional
    public Child editChild(Child child) {
        Child editedChild = childRepository.findById(child.getId())
                .orElseThrow(() -> new EntityNotFoundException(Child.class, child.getId()));
        editedChild.setFirstName(child.getFirstName());
        editedChild.setBirthDate(child.getBirthDate());
        editedChild.getConsumedMeals()
                .addAll(requireNonNull(mealCopyingService.copyPreDefinedMeals(child.getConsumedMeals(), editedChild.getConsumedMeals())));
        editedChild.getFavouriteAndDislikedIngredients()
                .addAll(requireNonNull(predefinedIngredientCopyingService.copyPreDefinedIngredients(child.getFavouriteAndDislikedIngredients(),
                        editedChild.getFavouriteAndDislikedIngredients())));
        return editedChild;
    }

    public void deleteChild(long id) {
        childRepository.deleteById(id);
    }

}
