package com.dietator.diet.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
    private Set<ConsumptionTime> consumptionTimes;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Child child;

    //TODO add child field to tests
    public Meal(long id, String designation, int energy, String preparationDescription, int preparationDuration, Set<ConsumptionTime> consumptionTimes, Set<Ingredient> ingredients, MealCategory mealCategory, PreparationDifficulty preparationDifficulty, boolean isPreDefined) {
        this.id = id;
        this.designation = designation;
        this.energy = energy;
        this.preparationDescription = preparationDescription;
        this.preparationDuration = preparationDuration;
        this.consumptionTimes = consumptionTimes;
        this.ingredients = ingredients;
        this.mealCategory = mealCategory;
        this.preparationDifficulty = preparationDifficulty;
        this.isPreDefined = isPreDefined;
    }

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
