package com.dietator.diet.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
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

    public Ingredient(Ingredient ingredientToClone) {
        this.designation = ingredientToClone.designation;
        this.energyPer100Grams = ingredientToClone.energyPer100Grams;
        this.weightPerMeal = ingredientToClone.weightPerMeal;
        this.isFavourite = ingredientToClone.isFavourite;
        this.isDisliked = ingredientToClone.isDisliked;
    }
}
