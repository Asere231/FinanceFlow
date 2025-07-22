package com.example.categoryservice.dto;

import com.example.categoryservice.model.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(
        @NotBlank String name,
        @NotBlank String icon,
        @NotBlank String color,
        @NotNull CategoryType type,
        Long parentId,
        String description
) { }
