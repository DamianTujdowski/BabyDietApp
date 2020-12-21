package com.dietator.diet.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 20, message
            = "designation must be between 3 and 20 characters")
    private String designation;

    @Min(value = 0, message = "energy can not be smaller than zero")
    private int energyPer100Grams;

    @Min(value = 0, message = "weight can not be smaller than zero")
    private int weightPerMeal;

    private boolean isFavourite;

    private boolean isDisliked;

    @AssertFalse(message = "user can not define predefined ingredients")
    private boolean isPreDefined;

    @Min(value = 0, message = "meal id can not be smaller than zero")
    @Column(name = "meal_id")
    private Long mealId;

    public Ingredient(Ingredient ingredientToClone) {
        this.designation = ingredientToClone.designation;
        this.energyPer100Grams = ingredientToClone.energyPer100Grams;
        this.weightPerMeal = ingredientToClone.weightPerMeal;
        this.isFavourite = ingredientToClone.isFavourite;
        this.isDisliked = ingredientToClone.isDisliked;
        this.isPreDefined = false;
    }
}
