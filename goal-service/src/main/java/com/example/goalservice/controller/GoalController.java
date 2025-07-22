package com.example.goalservice.controller;

import com.example.goalservice.dto.*;
import com.example.goalservice.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping("goals")
    public ResponseEntity<ApiResponse<GetAllGoalsResponse>> getAllGoals() {
        var payload = goalService.getAllGoals();
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got all goals!!!"));
    }

    @PostMapping("goal")
    public ResponseEntity<ApiResponse<SingleGoalResponse>> createGoal(@RequestBody CreateGoalRequest createGoalRequest) {
        var payload = goalService.createGoal(createGoalRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Goal created!!!"));
    }

    @GetMapping("goal/{id}")
    public ResponseEntity<ApiResponse<SingleGoalResponse>> getGoalById(@PathVariable Long id) {
        var payload = goalService.getGoalById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got goal!!!"));
    }

    @PutMapping("goal/{id}")
    public ResponseEntity<ApiResponse<SingleGoalResponse>> updateGoal(@PathVariable Long id, @RequestBody UpdateGoalRequest updateGoalRequest) {
        var payload = goalService.updateGoal(id, updateGoalRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Goal updated!!!"));
    }

    @DeleteMapping("goal/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "Goal deleted!!!"));
    }

    @GetMapping("goal/{id}/progress")
    public ResponseEntity<ApiResponse<GoalProgressResponse>> getGoalProgress(@PathVariable Long id) {
        var payload = goalService.getGoalProgress(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got goal!!!"));
    }

    @PostMapping("goal/{id}/contribute")
    public ResponseEntity<ApiResponse<SingleGoalResponse>> contributeGoal(@PathVariable Long id, @RequestBody AddContributionRequest addContributionRequest) {
        var payload = goalService.contributeGoal(id, addContributionRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Goal updated!!!"));
    }

    @GetMapping("goal/achieved")
    public ResponseEntity<ApiResponse<GetAllGoalsResponse>> getAchievedGoal() {
        var payload = goalService.getAchievedGoal();
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Goal updated!!!"));
    }
}
