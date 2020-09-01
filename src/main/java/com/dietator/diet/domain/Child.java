package com.dietator.diet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private LocalDate birthDate;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "child_id")
    private Set<Meal> consumedMeals;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "child_id")
    private Set<Ingredient> favouriteAndDislikedIngredients;

}
