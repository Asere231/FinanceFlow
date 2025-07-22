package com.example.categoryservice.dto;

import java.util.List;

public record SpendingTrendResponse(
        List<SpendingTrendDto> trend
) { }
