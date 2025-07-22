package com.example.transactionservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record SplitTransactionRequest(
        @NotNull
        Long originalTransactionId,
        @NotEmpty
        List<@Valid SplitDetailDto> splits
) { }