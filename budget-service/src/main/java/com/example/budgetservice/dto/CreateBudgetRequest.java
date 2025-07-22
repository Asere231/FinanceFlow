package com.example.budgetservice.dto;

import com.example.budgetservice.model.BudgetPeriod;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record CreateBudgetRequest(
        @NotBlank String name,
        @NotNull @DecimalMin("0.0") BigDecimal amount,
        @NotNull BudgetPeriod period,
        Set<Long> categoryIds,
        LocalDate startDate,
        LocalDate endDate,
        @Min(0) @Max(100) Integer alertThreshold
) { }

