package com.dietator.diet.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "child", orphanRemoval = true)
    private Set<Meal> consumedMeals;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "child_id")
    private Set<Ingredient> favouriteAndDislikedIngredients;

}
