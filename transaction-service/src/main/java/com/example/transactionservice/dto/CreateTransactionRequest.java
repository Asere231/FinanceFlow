package com.example.transactionservice.dto;

import com.example.transactionservice.model.TransactionType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record CreateTransactionRequest(
        @NotNull
        @DecimalMin(value = "0.00", inclusive = false)
        BigDecimal amount,
        @NotBlank
        String description,
        @NotNull
        Long accountId,
        @NotNull
        Long categoryId,
        @NotNull
        TransactionType type,
        Instant date,
        List<@NotBlank String> tags,
        String notes
) { }