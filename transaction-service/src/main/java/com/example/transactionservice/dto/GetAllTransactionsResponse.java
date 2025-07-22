package com.example.transactionservice.dto;

import java.util.List;

public record GetAllTransactionsResponse(
        List<TransactionResponse> transactions,
        PaginationDto pagination
) { }
