package com.dietator.diet.repository;

import com.dietator.diet.domain.Meal;
import com.dietator.diet.projections.MealInfo;
import com.dietator.diet.projections.statistics_projections.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;


@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    @QueryHints(@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false"))
    @Query("SELECT DISTINCT m " +
            "FROM Meal m " +
            "LEFT JOIN FETCH m.ingredients " +
            "RIGHT JOIN FETCH m.consumptionTimes " +
            "WHERE m.childId = :id")
    List<MealInfo> findAllById(@Param("id") long id);

    @Query("SELECT m.designation AS designation," +
            "COUNT(m.id) AS consumptionsQuantity " +
            "FROM Meal m " +
            "RIGHT JOIN m.consumptionTimes " +
            "WHERE m.childId = :id " +
            "GROUP BY m.id")
    List<MealsConsumptionQuantity> countMealsConsumptionQuantity(@Param("id") long id, Pageable page);

    @Query(value = "SELECT COUNT(*) AS consumedMealsNumber, " +
            "COUNT(*) / COUNT(DISTINCT DATE(consumption_time)) AS mealsDailyAverage " +
            "FROM consumption_time t " +
            "LEFT JOIN meal m ON t.meal_id = m.id " +
            "WHERE m.child_id = :id",
            nativeQuery = true)
    ConsumedMealsNumberAndDailyAverage countConsumedMealsNumberAndDailyAverage(@Param("id") long id);

    @Query("SELECT m.preparationDifficulty AS difficulty, " +
            "COUNT (m.id) AS mealsNumber " +
            "FROM Meal m " +
            "RIGHT JOIN m.consumptionTimes " +
            "WHERE m.childId = :id " +
            "GROUP BY m.preparationDifficulty " +
            "ORDER BY mealsNumber DESC")
    List<MealsPerDifficultyNumber> countMealsPerDifficultyNumber(@Param("id") long id);

    @Query("SELECT m.mealCategory AS category, " +
            "COUNT (m.id) AS mealsNumber " +
            "FROM Meal m " +
            "RIGHT JOIN m.consumptionTimes " +
            "WHERE m.childId = :id " +
            "GROUP BY m.mealCategory " +
            "ORDER BY mealsNumber DESC")
    List<MealsPerCategoryNumber> countMealsPerCategoryNumber(@Param("id") long id);

    @Query("SELECT m.designation AS designation, " +
            "SUM (m.energy) AS caloriesSum " +
            "FROM Meal m " +
            "RIGHT JOIN m.consumptionTimes " +
            "WHERE m.childId = :id " +
            "GROUP BY m.id")
    List<MealsConsumedCalories> countMealsConsumedCalories(@Param("id") long id, Pageable page);

    @Query(value = "SELECT DATE(t.consumption_time) AS consumptionDate, " +
            "SUM(m.energy) AS dailyCaloriesSum " +
            "FROM meal m " +
            "RIGHT JOIN consumption_time t ON m.id = t.meal_id " +
            "WHERE m.child_id = :id " +
            "GROUP BY consumptionDate " +
            "ORDER BY dailyCaloriesSum DESC",
            countQuery = "SELECT COUNT(DISTINCT DATE(t.consumption_time)) " +
                    "FROM meal m " +
                    "RIGHT JOIN consumption_time t ON m.id = t.meal_id",
            nativeQuery = true)
    List<DailyConsumedCalories> countDailyConsumedCalories(@Param("id") long id, Pageable pageable);

    @Query(value = "SELECT SUM(m.energy) AS consumedCaloriesSum, " +
            "SUM(m.energy) / COUNT(*) AS consumedCaloriesDailyAverage " +
            "FROM meal m " +
            "RIGHT JOIN consumption_time t ON m.id = t.meal_id " +
            "WHERE m.child_id = :id",
            nativeQuery = true)
    ConsumedCaloriesSumWithDailyAverage countConsumedCaloriesSumWithDailyAverage(@Param("id") long id);

    @Query("SELECT m.designation AS designation, " +
            "SUM (i.weightPerMeal) AS gramsSum " +
            "FROM Meal m " +
            "LEFT JOIN m.ingredients i " +
            "RIGHT JOIN m.consumptionTimes " +
            "WHERE m.childId = :id " +
            "GROUP BY m.id")
    List<MealsConsumedGrams> countMealsConsumedGrams(@Param("id") long id, Pageable pageable);

    @Query(value = "SELECT DATE(t.consumption_time) AS consumptionDate, " +
            "SUM(i.weight_per_meal) AS dailyConsumedGrams  " +
            "FROM meal m " +
            "LEFT JOIN ingredient i ON m.id = i.meal_id " +
            "RIGHT JOIN consumption_time t ON m.id = t.meal_id " +
            "WHERE m.child_id = :id " +
            "GROUP BY consumptionDate " +
            "ORDER BY dailyConsumedGrams DESC",
            countQuery = "SELECT COUNT(DISTINCT DATE(t.consumption_time)) " +
                    "FROM meal m " +
                    "RIGHT JOIN consumption_time t ON m.id = t.meal_id",
            nativeQuery = true)
    List<DailyConsumedGrams> countDailyConsumedGrams(@Param("id") long id, Pageable pageable);

    @Query(value = "SELECT SUM(i.weight_per_meal) AS consumedGramsOverallSum, " +
            "SUM(i.weight_per_meal) / COUNT(DISTINCT DATE(t.consumption_time)) AS consumedGramsDailyAverage " +
            "FROM meal m " +
            "LEFT JOIN ingredient i ON m.id = i.meal_id " +
            "RIGHT JOIN consumption_time t ON m.id = t.meal_id " +
            "WHERE m.child_id = :id",
            nativeQuery = true)
    ConsumedGramsSumWithDailyAverage countConsumedGramsSumWithDailyAverage(@Param("id") long id);

    @Query(value = "SELECT DATE(t.consumption_time) AS consumptionDate, " +
            "SUM(m.preparation_duration) AS dailyCookingTime " +
            "FROM meal m " +
            "RIGHT JOIN consumption_time t ON m.id = t.meal_id " +
            "WHERE m.child_id = :id " +
            "GROUP BY consumptionDate " +
            "ORDER BY dailyCookingTime DESC",
            countQuery = "SELECT COUNT(DISTINCT DATE(t.consumption_time)) " +
                    "FROM meal m " +
                    "RIGHT JOIN consumption_time t ON m.id = t.meal_id",
            nativeQuery = true)
    List<DailyCookingTime> countDailyCookingTime(@Param("id") long id, Pageable pageable);

    @Query(value = "SELECT SUM(m.preparation_duration) AS preparationDurationOverallSum, " +
            "SUM(m.preparation_duration) / COUNT(DISTINCT DATE(consumption_time)) AS preparationDurationDailyAverage " +
            "FROM meal m " +
            "RIGHT JOIN consumption_time t ON m.id = t.meal_id " +
            "WHERE m.child_id = :id",
            nativeQuery = true)
    TimeSpentCookingSum countTimeSpentCookingSum(@Param("id") long id);
}
