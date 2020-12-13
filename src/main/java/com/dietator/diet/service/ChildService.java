package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.projections.ChildInfo;
import com.dietator.diet.repository.ChildRepository;
import com.dietator.diet.utils.PredefinedIngredientCopier;
import com.dietator.diet.utils.PredefinedMealCopier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;


@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;

    private final PredefinedMealCopier mealCopyingService;

    private final PredefinedIngredientCopier predefinedIngredientCopier;

    public ChildInfo findById(long id) {
        return childRepository.findChildById(id).orElseThrow(() -> new EntityNotFoundException(Child.class, id));
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
                .addAll(requireNonNull(predefinedIngredientCopier.copyPreDefinedIngredients(child.getFavouriteAndDislikedIngredients(),
                        editedChild.getFavouriteAndDislikedIngredients())));
        return editedChild;
    }

    public void deleteChild(long id) {
        checkIfExists(id);
        childRepository.deleteById(id);
    }

    private void checkIfExists(long id) {
        if (!childRepository.existsById(id)) {
            throw new EntityNotFoundException(Child.class, id);
        }
    }

}
