package com.example.accountservice.dto;

import java.util.List;

public record GetAllTransactionsResponse(
        List<TransactionResponse> transactions,
        PaginationDto pagination
) { }
