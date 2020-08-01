package com.dietator.diet.repository;

import com.dietator.diet.domain.User;
import com.dietator.diet.projections.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u left join fetch u.children")
    List<UserInfo> findAllBy();

    Optional<UserInfo> findUserById(int id);
}
