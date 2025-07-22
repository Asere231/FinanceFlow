package com.example.transactionservice.dto;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) { }
