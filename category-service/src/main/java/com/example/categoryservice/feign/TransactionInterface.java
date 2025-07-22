package com.example.categoryservice.feign;

import com.example.categoryservice.dto.ApiResponse;
import com.example.categoryservice.dto.GetAllTransactionsResponse;
import com.example.categoryservice.model.TransactionType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Optional;

@Component
@FeignClient("TRANSACTION-SERVICE")
public interface TransactionInterface {

    @GetMapping("transactions")
    ResponseEntity<ApiResponse<GetAllTransactionsResponse>> getAllTransactions(
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> offset,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Long> accountId,
            @RequestParam Optional<Long> categoryId,
            @RequestParam Optional<TransactionType> type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<Instant> dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<Instant> dateTo
    );
}
