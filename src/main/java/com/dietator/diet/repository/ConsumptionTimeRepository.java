package com.dietator.diet.repository;

import com.dietator.diet.domain.ConsumptionTime;
import com.dietator.diet.projections.statistics_projections.MealConsumptionsNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionTimeRepository extends JpaRepository<ConsumptionTime, Long> {

    @Query("SELECT MAX(id) as consumptionsNumber FROM  ConsumptionTime")
    MealConsumptionsNumber getMealConsumptionsNumber();

}
