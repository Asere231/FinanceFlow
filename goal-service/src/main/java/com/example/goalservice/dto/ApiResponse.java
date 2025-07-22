package com.example.goalservice.dto;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) { }
