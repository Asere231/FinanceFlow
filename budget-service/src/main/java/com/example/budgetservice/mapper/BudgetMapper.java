// src/main/java/com/example/budgetservice/mapper/BudgetMapper.java
package com.example.budgetservice.mapper;

import com.example.budgetservice.dto.*;
import com.example.budgetservice.model.Budget;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BudgetMapper {

    /**
     * Convert CreateBudgetRequest to a new Budget entity.
     */
    public Budget toEntity(CreateBudgetRequest req) {
        return Budget.builder()
                .name(req.name())
                .amount(req.amount())
                .period(req.period())
                .categoryIds(req.categoryIds() != null ? Set.copyOf(req.categoryIds()) : null)
                .startDate(req.startDate())
                .endDate(req.endDate())
                .alertThreshold(req.alertThreshold())
                .build();
    }

    /**
     * Apply UpdateBudgetRequest fields onto an existing Budget.
     */
    public void updateEntity(UpdateBudgetRequest req, Budget budget) {
        if (req.name() != null) {
            budget.setName(req.name());
        }
        if (req.amount() != null) {
            budget.setAmount(req.amount());
        }
        if (req.period() != null) {
            budget.setPeriod(req.period());
        }
        if (req.categoryIds() != null) {
            budget.setCategoryIds(Set.copyOf(req.categoryIds()));
        }
        if (req.alertThreshold() != null) {
            budget.setAlertThreshold(req.alertThreshold());
        }
        // JPA @LastModifiedDate will set updatedAt
    }

    /**
     * Convert a Budget entity to its DTO representation.
     */
    public BudgetResponse toResponse(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getName(),
                budget.getAmount(),
                budget.getPeriod(),
                budget.getCategoryIds(),
                budget.getStartDate(),
                budget.getEndDate(),
                budget.getAlertThreshold(),
                budget.getCreatedAt(),
                budget.getUpdatedAt()
        );
    }

    /**
     * Convert a list of Budget entities to a list of DTOs.
     */
    public List<BudgetResponse> toResponseList(List<Budget> budgets) {
        return budgets.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Wrap a list of Budget entities in a BudgetsListResponse.
     */
    public BudgetsListResponse toListResponse(List<Budget> budgets) {
        return new BudgetsListResponse(toResponseList(budgets));
    }

    /**
     * Wrap a single Budget entity in a SingleBudgetResponse.
     */
    public SingleBudgetResponse toSingleResponse(Budget budget) {
        return new SingleBudgetResponse(toResponse(budget));
    }
}
