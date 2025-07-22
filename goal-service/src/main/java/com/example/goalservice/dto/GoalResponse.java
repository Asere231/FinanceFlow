package com.example.goalservice.dto;

import com.example.goalservice.model.GoalType;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record GoalResponse(
        Long id,
        String name,
        String description,
        GoalType type,
        BigDecimal targetAmount,
        BigDecimal currentAmount,
        LocalDate targetDate,
        Instant createdAt,
        Instant updatedAt
) { }
