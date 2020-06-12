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
    private double weight;
    private int kcal;
    private LocalDateTime consumptionTime;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MealCategory mealCategory;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "meal_id", updatable = false, insertable = false)
    private Set<Ingredient> ingredients;

    public Meal(String designation, double weight, int kcal, MealCategory mealCategory) {
        this.designation = designation;
        this.weight = weight;
        this.kcal = kcal;
        this.mealCategory = mealCategory;
    }
}
