package com.example.categoryservice.dto;

import java.util.List;

public record GetAllCategoriesResponse(
        List<CategoryResponse> categories
) { }
