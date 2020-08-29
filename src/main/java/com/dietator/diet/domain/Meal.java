package com.dietator.diet.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String designation;

    private int energy;

    private String preparationDescription;

    private int preparationDuration;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "meal_id")
    private Set<ConsumptionTime> consumptionTime;

    @OneToMany
    @JoinColumn(name = "meal_id")
    private Set<Ingredient> ingredients;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MealCategory mealCategory;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private PreparationDifficulty preparationDifficulty;

    private boolean isPrePrepared;

    public Meal(Meal mealToClone) {
        this.designation = mealToClone.designation;
        this.energy = mealToClone.energy;
        this.preparationDescription = mealToClone.preparationDescription;
        this.preparationDuration = mealToClone.preparationDuration;
        this.consumptionTime = cloneConsumptionTime(mealToClone);
        this.ingredients = cloneIngredients(mealToClone);
        this.mealCategory = mealToClone.mealCategory;
        this.preparationDifficulty = mealToClone.preparationDifficulty;
        this.isPrePrepared = false;
    }

    private Set<ConsumptionTime> cloneConsumptionTime(Meal mealToClone) {
        return mealToClone.consumptionTime
                .stream()
                .map(ConsumptionTime::new)
                .collect(Collectors.toSet());
    }

    private Set<Ingredient> cloneIngredients(Meal mealToClone) {
        return mealToClone.ingredients
                .stream()
                .map(Ingredient::new)
                .collect(Collectors.toSet());
    }
}
