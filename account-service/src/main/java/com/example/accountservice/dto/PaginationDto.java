package com.example.accountservice.dto;

import org.springframework.data.domain.Page;

public record PaginationDto(
        long total,
        int page,
        int limit,
        int totalPages
) {
    public static PaginationDto fromPage(Page<?> p) {
        return new PaginationDto(
                p.getTotalElements(),
                p.getNumber(),
                p.getSize(),
                p.getTotalPages()
        );
    }
}
