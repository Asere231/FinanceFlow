package com.example.transactionservice.feign;

import com.example.transactionservice.dto.AccountDto;
import com.example.transactionservice.dto.ApiResponse;
import com.example.transactionservice.dto.BalanceAdjustmentRequest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@Component
@FeignClient("ACCOUNT-SERVICE")
public interface AccountInterface {

    @GetMapping("account/{id}")
    ResponseEntity<ApiResponse<AccountDto>> getAccountById(@PathVariable Long id);

    @PostMapping("/api/accounts/{id}/balance/adjust")
    ResponseEntity<ApiResponse<Void>> adjustAccountBalance(
            @PathVariable("id") Long id,
            @RequestBody BalanceAdjustmentRequest req
    );

}
