package com.example.budgetservice.dto;

import com.example.budgetservice.model.BudgetPeriod;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

public record UpdateBudgetRequest(
        String name,
        @DecimalMin("0.0") BigDecimal amount,
        BudgetPeriod period,
        Set<Long> categoryIds,
        @Min(0) @Max(100) Integer alertThreshold
) { }

