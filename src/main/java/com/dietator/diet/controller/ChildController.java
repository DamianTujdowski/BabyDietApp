package com.dietator.diet.controller;

import com.dietator.diet.domain.Child;
import com.dietator.diet.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChildController {

    private final ChildService childService;

    @GetMapping("/children/{id}")
    public Child getChildById(@PathVariable int id) {
        return childService.getChildById(id);
    }

    @GetMapping("/children")
    public List<Child> findAllChildern() {
        return childService.findAllChildren();
    }

    @PostMapping("/children")
    public Child saveChild(@RequestBody Child child) {
        return childService.saveChild(child);
    }

    @PutMapping("/children")
    public Child editChild(@RequestBody Child child) {
        return childService.editChild(child);
    }

    @DeleteMapping("/children/{id}")
    public void deleteChild(@PathVariable int id) {
        childService.deleteChild(id);
    }
}