package com.dietator.diet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String designation;
    @OneToMany
    @JoinColumn(name = "meal_id")
    private Set<Ingredient> ingredients;
    private double weight;
    private int kcal;
    private LocalDateTime consumptionTime;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MealCategory mealCategory;

    public Meal(String designation, double weight, int kcal, MealCategory mealCategory) {
        this.designation = designation;
        this.weight = weight;
        this.kcal = kcal;
        this.mealCategory = mealCategory;
    }
}
