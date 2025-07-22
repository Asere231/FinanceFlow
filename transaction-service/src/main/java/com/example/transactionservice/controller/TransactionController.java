package com.example.transactionservice.controller;

import com.example.transactionservice.dto.*;
import com.example.transactionservice.model.TransactionType;
import com.example.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("transactions")
    public ResponseEntity<ApiResponse<GetAllTransactionsResponse>> getAllTransactions(
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> offset,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Long> accountId,
            @RequestParam Optional<Long> categoryId,
            @RequestParam Optional<TransactionType> type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<Instant> dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<Instant> dateTo
    ) {
        var payload = transactionService.getAllTransactions(limit, offset, page, accountId, categoryId, type, dateFrom, dateTo);
        return ResponseEntity.ok(new ApiResponse<>(true, payload, "Got all transactions!!!"));
    }

    @GetMapping("transactions/{accountId")
    public ResponseEntity<ApiResponse<GetAllTransactionsResponse>> getAllTransactionsByAccountId(@PathVariable Long accountId) {
        var payload = transactionService.getAllTransactionsByAccountId(accountId);
        return ResponseEntity.ok(new ApiResponse<>(true, payload, "Got all transactions!!!"));
    }

    @PostMapping("transaction")
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest) {
        var payload = transactionService.createTransaction(createTransactionRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, payload, "Transaction created!"));
    }

    @GetMapping("transaction/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(@PathVariable Long id) {
        var payload = transactionService.getTransactionById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, payload, "Transaction found!!!"));
    }

    @PutMapping("transaction/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction(@PathVariable Long id, @RequestBody UpdateTransactionRequest updateTransactionRequest) {
        var payload = transactionService.updateTransaction(id, updateTransactionRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, payload, "Transaction updated!!!"));
    }

    @DeleteMapping("transaction/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable Long id, @RequestBody UpdateTransactionRequest updateTransactionRequest) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Transaction deleted!!!"));
    }

    @GetMapping("transactions/search")
    public ResponseEntity<ApiResponse<TransactionsListResponse>> search(
            @RequestParam Optional<Long>         category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<Instant> dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<Instant> dateTo,
            @RequestParam Optional<BigDecimal>    amount,
            @RequestParam Optional<String>        description
    ) {
        var payload = transactionService.searchTransactions(category, dateFrom, dateTo, amount, description);
        return ResponseEntity.ok(new ApiResponse<>(true, payload, "Search completed"));
    }

    @PostMapping("transaction/transfer")
    public ResponseEntity<ApiResponse<TransactionsListResponse>> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        var payload = transactionService.transfer(transferRequest);
        return ResponseEntity
                .ok(new ApiResponse<>(true, payload, "Transfer completed"));
    }

    @PostMapping("transaction/split")
    public ResponseEntity<ApiResponse<TransactionsListResponse>> split(@RequestBody SplitTransactionRequest splitTransactionRequest) {
        var payload = transactionService.split(splitTransactionRequest);
        return ResponseEntity
                .ok(new ApiResponse<>(true, payload, "Split completed"));
    }

    @GetMapping("transactions/pending")
    public ResponseEntity<ApiResponse<TransactionsListResponse>> getPendingTransactions() {
        var payload = transactionService.getPendingTransactions();
        return ResponseEntity
                .ok(new ApiResponse<>(true, payload, "Got pending Transactions"));
    }

    @PutMapping("transaction/{id}/approve")
    public ResponseEntity<ApiResponse<TransactionResponse>> approveTransaction(@PathVariable Long id) {
        var payload = transactionService.approveTransaction(id);
        return ResponseEntity
                .ok(new ApiResponse<>(true, payload, "Approved Transactions"));
    }

    @PostMapping("transaction/sum")
    public ResponseEntity<ApiResponse<SumTransactionsResponse>> sumTransactions(
            @RequestParam List<Long> categoryIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate
    ) {
        SumTransactionsResponse response = transactionService.sumTransactions(
                categoryIds, startDate, endDate
        );
        return ResponseEntity.ok(
                new ApiResponse<>(true, response, "Sum calculated successfully")
        );
    }
}
