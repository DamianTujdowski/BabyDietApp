package com.dietator.diet.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nickname;
    private String password;
    private String email;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Set<Child> children;
}
