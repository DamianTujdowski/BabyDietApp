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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private LocalDateTime birthDate;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_id")
    private List<Meal> consumedMeals;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_id")
    private List<Drink> consumedDrinks;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_id")
    private Set<Ingredient> likes;
//    private Set<Ingredient> dislikes;

    public Baby(String firstName, LocalDateTime birthDate) {
        this.firstName = firstName;
        this.birthDate = birthDate;
    }
}
