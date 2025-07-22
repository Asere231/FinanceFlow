package com.example.budgetservice.dto;

import java.math.BigDecimal;

public record BudgetProgressDto(
        BigDecimal spent,
        BigDecimal remaining,
        double percentage
) { }

