package com.dietator.diet.repository;

import com.dietator.diet.domain.Child;
import com.dietator.diet.dto.ChildBasicInfo;
import com.dietator.diet.dto.ChildDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {

    //TODO Hibernate changes ingredients left join to join
//    @Query("select ch from Child ch left join fetch ch.consumedMeals m" +
//            "left join fetch ch.favouriteAndDislikedIngredients i")
    List<ChildBasicInfo> findAllChildrenBy();
}
