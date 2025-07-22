package com.example.transactionservice.dto;

import java.util.List;

public record TransactionsListResponse(
        List<TransactionResponse> transactions
) { }
