package com.example.goalservice.dto;

import com.example.goalservice.model.GoalType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateGoalRequest(
        @NotBlank String name,
        String description,
        @NotNull GoalType type,
        @NotNull @DecimalMin("0.0") BigDecimal targetAmount,
        @DecimalMin("0.0") BigDecimal currentAmount,
        LocalDate targetDate
) { }
