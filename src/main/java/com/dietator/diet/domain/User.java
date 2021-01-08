package com.dietator.diet.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 20, message
            = "must be between 3 and 20 characters")
    private String nickname;

    @Min(value = 6, message = "must contain at least 6 characters")
    @Pattern(regexp = ".*\\d.*", message = "must contain at least one digit")
    private String password;

    @Email(message = "incorrect address")
    private String email;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Set<Child> children;
}
