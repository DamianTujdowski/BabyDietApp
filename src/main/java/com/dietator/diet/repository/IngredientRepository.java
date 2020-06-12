package com.dietator.diet.repository;

import com.dietator.diet.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    @Query("select distinct i from ingredients i")
    List<Ingredient> findAll();
}
