package com.example.goalservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record AddContributionRequest(
        @NotNull @DecimalMin("0.0") BigDecimal amount,
        String note
) { }
