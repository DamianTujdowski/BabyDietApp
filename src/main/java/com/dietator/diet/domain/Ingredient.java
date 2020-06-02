package com.dietator.diet.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String designation;
    private int kcal;
    private boolean isFavourite;
    private boolean isDisliked;

    public Ingredient(String designation, int kcal) {
        this.designation = designation;
        this.kcal = kcal;
    }
}
