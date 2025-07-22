package com.example.budgetservice.dto;

import com.example.budgetservice.model.BudgetPeriod;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record BudgetResponse(
        Long id,
        String name,
        BigDecimal amount,
        BudgetPeriod period,
        Set<Long> categoryIds,
        LocalDate startDate,
        LocalDate endDate,
        Integer alertThreshold,
        Instant createdAt,
        Instant updatedAt
) { }

