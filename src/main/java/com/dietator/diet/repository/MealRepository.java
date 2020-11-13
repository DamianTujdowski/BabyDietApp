package com.dietator.diet.repository;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.projections.statistics_projections.ConsumedMealsNumberAndDailyAverage;
import com.dietator.diet.projections.statistics_projections.MealsConsumptionQuantity;
import com.dietator.diet.projections.statistics_projections.MealsPerDifficultyNumber;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;


@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    @QueryHints(@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false"))
    @Query("SELECT DISTINCT m " +
            "FROM Meal m " +
            "LEFT JOIN FETCH m.ingredients")
    List<MealInfo> findAllBy();

    @Query("SELECT m.designation AS designation," +
            "COUNT(m.id) AS consumptionsQuantity " +
            "FROM Meal m " +
            "RIGHT JOIN m.consumptionTimes " +
            "WHERE m.childId = :id " +
            "GROUP BY m.id")
    List<MealsConsumptionQuantity> getMealsConsumptionQuantity(@Param("id") long id, Pageable page);

    @Query(value = "SELECT COUNT(*) AS consumedMealsNumber, " +
            "COUNT(*) / COUNT(DISTINCT DATE(consumption_time)) AS mealsPerDayAverage " +
            "FROM consumption_time t " +
            "LEFT JOIN meal m ON t.meal_id = m.id " +
            "WHERE m.child_id = :id",
            nativeQuery = true)
    ConsumedMealsNumberAndDailyAverage getConsumedMealsNumberAndDailyAverage(@Param("id") long id);

    @Query("SELECT m.preparationDifficulty AS difficulty, " +
            "COUNT (m.id) AS mealsNumber " +
            "FROM Meal m " +
            "RIGHT JOIN m.consumptionTimes " +
            "WHERE m.childId = :id " +
            "GROUP BY m.preparationDifficulty " +
            "ORDER BY mealsNumber DESC")
    List<MealsPerDifficultyNumber> getMealsPerDifficultyNumber(@Param("id") long id);

}
