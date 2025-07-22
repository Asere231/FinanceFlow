package com.example.accountservice.feign;

import com.example.accountservice.dto.ApiResponse;
import com.example.accountservice.dto.GetAllTransactionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TRANSACTION-SERVICE")
public interface TransactionInterface {

    @GetMapping("transactions/{accountId")
    ResponseEntity<ApiResponse<GetAllTransactionsResponse>> getAllTransactionsByAccountId(@PathVariable Long accountId);
}
