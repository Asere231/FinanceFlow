package com.example.goalservice.dto;

import java.util.List;

public record AchievedGoalsResponse(
        List<GoalResponse> goals
) { }
