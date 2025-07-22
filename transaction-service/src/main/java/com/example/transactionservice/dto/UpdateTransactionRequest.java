package com.example.transactionservice.dto;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

public record UpdateTransactionRequest(
        @DecimalMin(value = "0.00", inclusive = false)
        BigDecimal amount,
        String description,
        Long categoryId,
        List<String> tags,
        String notes
) { }
