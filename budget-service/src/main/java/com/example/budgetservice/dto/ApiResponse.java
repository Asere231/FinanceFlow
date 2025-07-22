package com.example.budgetservice.dto;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) { }
