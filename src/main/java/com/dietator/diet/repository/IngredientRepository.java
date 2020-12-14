package com.dietator.diet.repository;

import com.dietator.diet.domain.Ingredient;
import com.dietator.diet.projections.IngredientBasicInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<IngredientBasicInfo> findByIsPreDefinedTrue(Pageable pageable);
}
