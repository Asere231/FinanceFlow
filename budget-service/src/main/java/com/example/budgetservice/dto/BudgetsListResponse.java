package com.example.budgetservice.dto;

import java.util.List;

public record BudgetsListResponse(
        List<BudgetResponse> budgets
) { }

