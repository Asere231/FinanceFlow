package com.example.categoryservice.dto;

import java.util.List;

public record GetAllTransactionsResponse(
        List<TransactionResponse> transactions,
        PaginationDto pagination
) { }
