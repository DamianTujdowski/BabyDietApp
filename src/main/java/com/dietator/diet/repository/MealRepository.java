package com.dietator.diet.repository;

import com.dietator.diet.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

    public Meal findByDesignation(String name);

}
