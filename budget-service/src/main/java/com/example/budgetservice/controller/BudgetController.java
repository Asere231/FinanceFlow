package com.example.budgetservice.controller;

import com.example.budgetservice.dto.*;
import com.example.budgetservice.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("budgets")
    public ResponseEntity<ApiResponse<BudgetsListResponse>> getAllBudgets() {
        var payload = budgetService.getAllBudgets();
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got all budgets!!!"));
    }

    @PostMapping("budget")
    public ResponseEntity<ApiResponse<SingleBudgetResponse>> createBudget(@RequestBody CreateBudgetRequest createBudgetRequest) {
        var payload = budgetService.createBudget(createBudgetRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Budget created!!!"));
    }

    @GetMapping("budget/{id}")
    public ResponseEntity<ApiResponse<SingleBudgetResponse>> getBudgetById(@PathVariable Long id) {
        var payload = budgetService.getBudgetById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got budget!!!"));
    }

    @PutMapping("budget/{id}")
    public ResponseEntity<ApiResponse<SingleBudgetResponse>> updateBudget(@PathVariable Long id, @RequestBody UpdateBudgetRequest updateBudgetRequest) {
        var payload = budgetService.updateBudget(id, updateBudgetRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Budget updated!!!"));
    }

    @DeleteMapping("budget/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "Budget deleted!!!"));
    }

    @GetMapping("budgets/current")
    public ResponseEntity<ApiResponse<BudgetsListResponse>> getCurrentBudgets() {
        var payload = budgetService.getCurrentBudgets();
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got all budgets!!!"));
    }

    @GetMapping("budget/{id}/progress")
    public ResponseEntity<ApiResponse<BudgetProgressResponse>> getBudgetProgress(@PathVariable Long id) {
        var payload = budgetService.getBudgetProgress(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got all budgets!!!"));
    }

    @PostMapping("budget/{id}/reset")
    public ResponseEntity<ApiResponse<SingleBudgetResponse>> resetBudget(@PathVariable Long id) {
        var payload = budgetService.resetBudget(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Budget updated!!!"));
    }

}
