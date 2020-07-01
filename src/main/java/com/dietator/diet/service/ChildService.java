package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;

    public Child getChildById(int id) {
        return childRepository.findById(id).orElseThrow();
    }

    public List<Child> findAllChildren() {
        return childRepository.findAll();
    }

    public Child saveChild(Child child) {
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
}
