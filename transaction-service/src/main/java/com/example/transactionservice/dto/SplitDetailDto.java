package com.example.transactionservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record SplitDetailDto(
        @NotNull @DecimalMin(value = "0.00", inclusive = false)
        BigDecimal amount,
        @NotNull
        Long categoryId,
        String description
) { }
