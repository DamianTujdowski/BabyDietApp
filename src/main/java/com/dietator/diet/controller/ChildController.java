package com.dietator.diet.controller;

import com.dietator.diet.domain.Child;
import com.dietator.diet.projections.ChildInfo;
import com.dietator.diet.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChildController {

    private final ChildService childService;

    @GetMapping("/children/{id}")
    public ChildInfo getChildById(@PathVariable long id) {
        return childService.findById(id);
    }

    @GetMapping("/children")
    public List<ChildInfo> findAllChildren() {
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
    public void deleteChild(@PathVariable long id) {
        childService.deleteChild(id);
    }
}
