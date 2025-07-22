package com.example.transactionservice.dto;

import java.math.BigDecimal;

public record SumTransactionsResponse(
        BigDecimal sum
) { }

