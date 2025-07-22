package com.example.goalservice.dto;

import java.util.List;

public record GetAllGoalsResponse(
        List<GoalResponse> goals
) { }
