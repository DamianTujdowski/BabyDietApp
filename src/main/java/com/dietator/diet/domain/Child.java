package com.dietator.diet.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private LocalDateTime birthDate;

    @OneToMany
    @JoinColumn(name = "baby_id")
    private List<Meal> consumedMeals;

    @OneToMany
    @JoinColumn(name = "baby_id")
    private Set<Ingredient> favouriteAndDislikedIngredients;

}
