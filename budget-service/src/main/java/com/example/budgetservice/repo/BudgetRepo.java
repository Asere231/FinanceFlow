package com.example.budgetservice.repo;

import com.example.budgetservice.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {

    @Query("""
      SELECT b FROM Budget b
      WHERE (b.startDate IS NULL OR b.startDate <= :today)
        AND (b.endDate   IS NULL OR b.endDate   >= :today)
      """)
    List<Budget> findCurrentBudgets(@Param("today") LocalDate today);
}
