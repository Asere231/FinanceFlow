package com.example.budgetservice.dto;

import java.math.BigDecimal;

public record SumTransactionsResponse(
        BigDecimal sum
) { }

