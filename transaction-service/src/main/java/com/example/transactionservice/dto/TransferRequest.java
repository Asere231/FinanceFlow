package com.example.transactionservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record TransferRequest(
        @NotNull
        Long fromAccountId,
        @NotNull
        Long toAccountId,
        @NotNull @DecimalMin(value = "0.00", inclusive = false)
        BigDecimal amount,
        @NotBlank
        String description
) { }