package com.dietator.diet.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
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

    @Size(min = 3, max = 20, message
            = "must be between 3 and 20 characters")
    private String firstName;

    @PastOrPresent(message = "must be in the past or in the present")
    private LocalDate birthDate;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "child_id")
    private Set<Meal> consumedMeals;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "child_id")
    private Set<Ingredient> favouriteAndDislikedIngredients;

}
