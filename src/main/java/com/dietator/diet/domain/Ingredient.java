package com.dietator.diet.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String designation;

    private int energyPer100Grams;

    private int weightPerMeal;

    //    private Baby baby;

    private boolean isFavourite;

    private boolean isDisliked;
}
