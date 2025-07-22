package com.example.categoryservice.dto;

import com.example.categoryservice.model.CategoryType;
import java.time.Instant;

public record CategoryResponse(
        Long id,
        String name,
        String icon,
        String color,
        CategoryType type,
        Long parentId,
        String description,
        Instant createdAt,
        Instant updatedAt
) { }
