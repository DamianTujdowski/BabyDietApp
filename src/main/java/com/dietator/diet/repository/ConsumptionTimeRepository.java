package com.dietator.diet.repository;

import com.dietator.diet.domain.ConsumptionTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionTimeRepository extends JpaRepository<ConsumptionTime, Long> {
}
