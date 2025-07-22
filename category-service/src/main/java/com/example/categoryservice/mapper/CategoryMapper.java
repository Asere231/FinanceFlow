package com.example.categoryservice.mapper;

import com.example.categoryservice.dto.*;
import com.example.categoryservice.model.Category;
import com.example.categoryservice.model.CategoryType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category toEntity(CreateCategoryRequest req) {
        Category.CategoryBuilder builder = Category.builder()
                .name(req.name())
                .icon(req.icon())
                .color(req.color())
                .type(req.type())
                .description(req.description())
                .createdAt(Instant.now())
                .updatedAt(Instant.now());
        if (req.parentId() != null) {
            builder.parent(Category.builder().id(req.parentId()).build());
        }
        return builder.build();
    }

    public void updateEntity(UpdateCategoryRequest req, Category cat) {
        if (req.name() != null)        cat.setName(req.name());
        if (req.icon() != null)        cat.setIcon(req.icon());
        if (req.color() != null)       cat.setColor(req.color());
        if (req.description() != null) cat.setDescription(req.description());
        cat.setUpdatedAt(Instant.now());
    }

    public CategoryResponse toResponse(Category cat) {
        Long parentId = cat.getParent() != null ? cat.getParent().getId() : null;
        return new CategoryResponse(
                cat.getId(),
                cat.getName(),
                cat.getIcon(),
                cat.getColor(),
                cat.getType(),
                parentId,
                cat.getDescription(),
                cat.getCreatedAt(),
                cat.getUpdatedAt()
        );
    }

    public List<CategoryResponse> toResponseList(List<Category> cats) {
        return cats.stream().map(this::toResponse).collect(Collectors.toList());
    }
}

