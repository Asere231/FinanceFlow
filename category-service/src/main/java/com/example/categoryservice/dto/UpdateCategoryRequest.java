package com.example.categoryservice.dto;

public record UpdateCategoryRequest(
        String name,
        String icon,
        String color,
        String description
) { }
