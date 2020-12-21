package com.dietator.diet.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(min = 3, max = 20, message
            = "designation must be between 3 and 20 characters")
    private String designation;

    @Min(value = 0, message = "energy can not be smaller than zero")
    private int energy;

    @Size(min = 20, max = 400, message
            = "description must be between 20 and 400 characters")
    private String preparationDescription;

    @Min(value = 0, message = "duration can not be smaller than zero")
    private int preparationDuration;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "meal_id")
    private Set<ConsumptionTime> consumptionTimes;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "meal_id")
    private Set<Ingredient> ingredients;

    @NotNull(message = "meal must be assigned to a category")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MealCategory mealCategory;

    @NotNull(message = "meal have to be assigned with a difficulty")
    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private PreparationDifficulty preparationDifficulty;

    @AssertFalse(message = "user can not define predefined meals")
    private boolean isPreDefined;

    @Min(value = 0, message = "child id can not be smaller than zero")
    @Column(name = "child_id")
    private Long childId;

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
