package com.dietator.diet.repository;

import com.dietator.diet.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Query("select m from Meal m left join fetch m.ingredients")
    Set<Meal> findAllMeals();

}
