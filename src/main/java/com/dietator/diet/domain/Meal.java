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

    @OneToMany
    @JoinColumn(name = "meal_id")
    private Set<ConsumptionTime> consumptionTime;

    //TODO repair cascading bug - save/persist does not work, merge works without annotation
    @OneToMany
    @JoinColumn(name = "meal_id")
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    @Column(insertable = false, updatable = false)
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
        this.consumptionTime = mealToClone.consumptionTime
                .stream()
                .map(ConsumptionTime::new)
                .collect(Collectors.toSet());
        this.ingredients = mealToClone.ingredients
                .stream()
                .map(Ingredient::new)
                .collect(Collectors.toSet());
        this.mealCategory = mealToClone.mealCategory;
        this.preparationDifficulty = mealToClone.preparationDifficulty;
        this.isPrePrepared = false;
    }
}
