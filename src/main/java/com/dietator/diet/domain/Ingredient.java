package com.dietator.diet.domain;


import lombok.*;

import javax.persistence.*;

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

    private String designation;

    private int energyPer100Grams;

    private int weightPerMeal;

    private boolean isFavourite;

    private boolean isDisliked;

    private boolean isPreDefined;

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
