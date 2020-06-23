package com.dietator.diet.controller;

import com.dietator.diet.domain.Baby;
import com.dietator.diet.service.BabyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BabyController {

    private final BabyService babyService;

    @GetMapping("/babies/{id}")
    public Baby getBabyById(@PathVariable int id) {
        return babyService.getBabyById(id);
    }

    @GetMapping("/babies")
    public List<Baby> findAllBabies() {
        return babyService.findAllBabies();
    }

    @PostMapping("/babies")
    public Baby saveBaby(Baby baby) {
        return babyService.saveBaby(baby);
    }

    @PutMapping("/babies")
    public Baby editBaby(Baby baby) {
        return babyService.editBaby(baby);
    }

    @DeleteMapping("/babies/{id}")
    public void deleteBaby(@PathVariable int id) {
        babyService.deleteBaby(id);
    }
}
