package com.dietator.diet.service;

import com.dietator.diet.domain.Baby;
import com.dietator.diet.repository.BabyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BabyService {

    private final BabyRepository babyRepository;

    public Baby getBabyById(int id) {
        return babyRepository.findById(id).orElseThrow();
    }

    public List<Baby> findAllBabies() {
        return babyRepository.findAll();
    }

    public Baby saveBaby(Baby baby) {
        return babyRepository.save(baby);
    }

    @Transactional
    public Baby editBaby(Baby baby) {
        Baby editedBaby = babyRepository.findById(baby.getId()).orElseThrow();
        editedBaby.setFirstName(baby.getFirstName());
        editedBaby.setBirthDate(baby.getBirthDate());
        editedBaby.setConsumedMeals(baby.getConsumedMeals());
        editedBaby.setFavouriteAndDislikedIngredients(baby.getFavouriteAndDislikedIngredients());
        return editedBaby;
    }

    public void deleteBaby(int id) {
        babyRepository.deleteById(id);
    }
}
