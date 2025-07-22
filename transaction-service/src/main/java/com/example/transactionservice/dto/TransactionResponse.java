package com.example.transactionservice.dto;

import com.example.transactionservice.model.TransactionStatus;
import com.example.transactionservice.model.TransactionType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record TransactionResponse(
        Long id,
        BigDecimal amount,
        String description,
        Long accountId,
        Long categoryId,
        TransactionType type,
        Instant date,
        List<String> tags,
        String notes,
        TransactionStatus status,
        Instant createdAt,
        Instant updatedAt
) { }