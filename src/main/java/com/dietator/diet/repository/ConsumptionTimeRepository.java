package com.dietator.diet.repository;

import com.dietator.diet.domain.ConsumptionTime;
import com.dietator.diet.projections.statistics_projections.AllConsumedMealsNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionTimeRepository extends JpaRepository<ConsumptionTime, Long> {

}
