package com.dietator.diet.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
}
