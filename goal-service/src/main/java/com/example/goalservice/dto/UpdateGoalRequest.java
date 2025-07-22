package com.example.goalservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateGoalRequest(
        String name,
        String description,
        @DecimalMin("0.0") BigDecimal targetAmount,
        LocalDate targetDate
) { }
