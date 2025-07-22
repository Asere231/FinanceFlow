package com.example.accountservice.controller;

import com.example.accountservice.dto.*;
import com.example.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("accounts")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts(Authentication auth) {
        return accountService.getAllAccounts(auth);
    }

    @GetMapping("account/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("account")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(Authentication auth, @RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(auth, accountRequest);
    }

    @PutMapping("account/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(Authentication auth, @RequestBody AccountRequest accountRequest, @PathVariable Long id) {
        return accountService.updateAccount(auth, accountRequest, id);
    }

    @DeleteMapping("account/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccount(id);
    }

    @GetMapping("account/{id}/balance")
    public ResponseEntity<ApiResponse<BalanceResponse>> getAccountBalance(@PathVariable Long id) {
        return accountService.getAccountBalance(id);
    }

    @PostMapping("account/{id}/balance/adjust")
    public ResponseEntity<ApiResponse<AccountResponse>> adjustAccountBalance(@PathVariable Long id, @RequestBody BigDecimal amount, @RequestBody String reason) {
        return accountService.adjustAccountBalance(id, amount, reason);
    }

    @GetMapping("account/{id}/transactions")
    public ResponseEntity<ApiResponse<GetAllTransactionsResponse>> getAccountTransactions(@PathVariable Long id) {
        return accountService.getAccountTransactions(id);
    }
}
