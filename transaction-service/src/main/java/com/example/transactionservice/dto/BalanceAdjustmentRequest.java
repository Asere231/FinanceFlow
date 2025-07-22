package com.example.transactionservice.dto;

import java.math.BigDecimal;

public record BalanceAdjustmentRequest(
        BigDecimal amount,
        String reason
) { }
