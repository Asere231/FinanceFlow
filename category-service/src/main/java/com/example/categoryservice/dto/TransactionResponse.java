package com.example.categoryservice.dto;

import com.example.categoryservice.model.TransactionStatus;
import com.example.categoryservice.model.TransactionType;

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