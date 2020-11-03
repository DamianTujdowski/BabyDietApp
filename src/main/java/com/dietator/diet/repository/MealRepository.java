package com.dietator.diet.repository;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.projections.MealConsumptionsCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;


@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    @QueryHints(@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false"))
    @Query("select distinct m from Meal m left join fetch m.ingredients")
    List<MealInfo> findAllBy();

    @Query("SELECT m.designation AS designation, COUNT() AS consumptionsCount FROM Meal m " +
            "RIGHT JOIN ConsumptionTime ct GROUP BY m.id ORDER BY consumptionsCount ASC")
    List<MealConsumptionsCount> countMealsByConsumpitonsCount();

}
