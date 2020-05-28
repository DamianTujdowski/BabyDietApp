package com.dietator.diet.repository;

import com.dietator.diet.domain.Meal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends CrudRepository<Meal, String> {

    public Meal findByDesignation(String name);
}
