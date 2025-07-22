package com.example.categoryservice.controller;

import com.example.categoryservice.dto.*;
import com.example.categoryservice.service.CategoryService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("categories")
    public ResponseEntity<ApiResponse<GetAllCategoriesResponse>> getAllCategories() {
        var payload = categoryService.getAllCategories();
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got all categories!!!"));
    }

    @PostMapping("category")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        var payload = categoryService.createCategory(createCategoryRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Category created!!!"));
    }

    @GetMapping("category/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        var payload = categoryService.getCategoryById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got category!!!"));
    }

    @PutMapping("category/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        var payload = categoryService.updateCategory(id, updateCategoryRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(true, payload, "Got category!!!"));
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "Category deleted!!!"));
    }

    @GetMapping("category/{id}/transactions")
    public ResponseEntity<ApiResponse<GetAllTransactionsResponse>> getTransactionsCategoryId(@PathVariable Long id) {
//        var payload = categoryService.getTransactionsCategoryId(id);
        return categoryService.getTransactionsCategoryId(id);
    }

    @GetMapping("category/{id}/spending-trend")
    public ResponseEntity<ApiResponse<Void>> getSpendingTrendCategoryId(@PathVariable Long id) {

        return categoryService.getSpendingTrendCategoryId(id);
    }
}
