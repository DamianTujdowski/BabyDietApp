package com.dietator.diet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Baby {

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
    private List<Drink> consumedDrinks;
    @OneToMany
    @JoinColumn(name = "baby_id")
    private Set<Ingredient> ingredients;


    public Baby(String firstName, LocalDateTime birthDate) {
        this.firstName = firstName;
        this.birthDate = birthDate;
    }
}
