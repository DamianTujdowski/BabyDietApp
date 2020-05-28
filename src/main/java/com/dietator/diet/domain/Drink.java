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
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String designation;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    private Set<Ingredient> ingredients;
    private double volume;
    private int kcal;
    private LocalDateTime consumptionTime;

    public Drink(String designation, double volume, int kcal) {
        this.designation = designation;
        this.volume = volume;
        this.kcal = kcal;
    }
}
