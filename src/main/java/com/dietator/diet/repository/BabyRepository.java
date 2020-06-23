package com.dietator.diet.repository;

import com.dietator.diet.domain.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BabyRepository extends JpaRepository<Baby, Integer> {
}
