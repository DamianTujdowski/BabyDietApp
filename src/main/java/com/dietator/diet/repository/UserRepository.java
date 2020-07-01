package com.dietator.diet.repository;

import com.dietator.diet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u left join fetch u.children")
    Set<User> findAllUsers();
}
