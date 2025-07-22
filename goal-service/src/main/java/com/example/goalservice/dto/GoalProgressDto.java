package com.example.goalservice.dto;

public record GoalProgressDto(
        double percentage,
        boolean onTrack,
        String projectedCompletion  // ISO date string, or null if not predictable
) { }
