package com.dietator.diet.domain;

import lombok.*;

import javax.persistence.*;
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

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "meal_id")
    private Set<Ingredient> ingredients;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MealCategory mealCategory;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private PreparationDifficulty preparationDifficulty;

    private boolean isPreDefined;

    public Meal(Meal mealToClone) {
        this.designation = mealToClone.designation;
        this.energy = mealToClone.energy;
        this.preparationDescription = mealToClone.preparationDescription;
        this.preparationDuration = mealToClone.preparationDuration;
        this.ingredients = cloneIngredients(mealToClone);
        this.mealCategory = mealToClone.mealCategory;
        this.preparationDifficulty = mealToClone.preparationDifficulty;
        this.isPreDefined = false;
    }

    private Set<Ingredient> cloneIngredients(Meal mealToClone) {
        return mealToClone.ingredients
                .stream()
                .map(Ingredient::new)
                .collect(Collectors.toSet());
    }
}
