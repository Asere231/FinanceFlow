package com.example.categoryservice.dto;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) { }
