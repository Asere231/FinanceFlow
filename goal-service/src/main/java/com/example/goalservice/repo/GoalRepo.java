package com.example.goalservice.repo;

import com.example.goalservice.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepo extends JpaRepository<Goal, Long> {

    @Query("""
    SELECT g
    FROM Goal g
    WHERE g.currentAmount >= g.targetAmount
    """)
    List<Goal> findAchievedGoals();

}
