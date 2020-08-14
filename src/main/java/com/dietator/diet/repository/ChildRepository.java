package com.dietator.diet.repository;

import com.dietator.diet.domain.Child;
import com.dietator.diet.projections.ChildInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    @QueryHints(@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false"))
    @Query("select distinct c from Child c left join fetch c.consumedMeals m")
    List<ChildInfo> findAllBy();
}
