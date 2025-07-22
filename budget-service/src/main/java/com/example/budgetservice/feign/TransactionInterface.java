package com.example.budgetservice.feign;

import com.example.budgetservice.dto.ApiResponse;
import com.example.budgetservice.dto.SumTransactionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@Component
@FeignClient("TRANSACTION-SERVICE")
public interface TransactionInterface {

    @PostMapping("transaction/sum")
    ResponseEntity<ApiResponse<SumTransactionsResponse>> sumTransactions(
            @RequestParam List<Long> categoryIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate
    );
}
