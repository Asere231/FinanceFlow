package com.example.categoryservice.service;

import com.example.categoryservice.dto.*;
import com.example.categoryservice.feign.TransactionInterface;
import com.example.categoryservice.mapper.CategoryMapper;
import com.example.categoryservice.model.Category;
import com.example.categoryservice.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper mapper;
    private final TransactionInterface transactionInterface;

    public GetAllCategoriesResponse getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return new GetAllCategoriesResponse(mapper.toResponseList(categories));
    }

    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = categoryRepo.save(mapper.toEntity(createCategoryRequest));
        return mapper.toResponse(category);
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepo.findById(id).get();
        return mapper.toResponse(category);
    }

    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepo.findById(id).get();

        if (!category.getName().equals(updateCategoryRequest.name()))
            category.setName(updateCategoryRequest.name());
        if (!category.getIcon().equals(updateCategoryRequest.icon()))
            category.setIcon(updateCategoryRequest.icon());
        if (!category.getColor().equals(updateCategoryRequest.color()))
            category.setColor(updateCategoryRequest.color());
        if (!category.getDescription().equals(updateCategoryRequest.description()))
            category.setDescription(updateCategoryRequest.description());

        Category cat = categoryRepo.save(category);

        return mapper.toResponse(cat);
    }

    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }


    public ResponseEntity<ApiResponse<GetAllTransactionsResponse>> getTransactionsCategoryId(Long id) {
        return transactionInterface.getAllTransactions(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(id),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());
    }

    public ResponseEntity<ApiResponse<Void>> getSpendingTrendCategoryId(Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
