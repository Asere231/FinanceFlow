package com.example.transactionservice.service;

import com.example.transactionservice.dto.*;
import com.example.transactionservice.exception.InsufficientFundsException;
import com.example.transactionservice.feign.AccountInterface;
import com.example.transactionservice.mapper.TransactionMapper;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.TransactionStatus;
import com.example.transactionservice.model.TransactionType;
import com.example.transactionservice.repo.TransactionRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final TransactionMapper mapper;
    private final AccountInterface accountInterface;

    public GetAllTransactionsResponse getAllTransactions(Optional<Integer> limit,
                                                         Optional<Integer> offset,
                                                         Optional<Integer> page,
                                                         Optional<Long> accountId,
                                                         Optional<Long> categoryId,
                                                         Optional<TransactionType> type,
                                                         Optional<Instant> dateFrom,
                                                         Optional<Instant> dateTo) {

        int pageIndex = page.orElse(0);
        int pageSize  = limit.orElse(20);

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        var transactions =
                transactionRepo.getAllTransactions(
                        accountId.orElse(null),
                        categoryId.orElse(null),
                        type.orElse(null),
                        dateFrom.orElse(null),
                        dateTo.orElse(null),
                        pageable
                );

        PaginationDto paginationDto = PaginationDto.fromPage(transactions);
        List<Transaction> transactionList = transactions.stream().toList();
        GetAllTransactionsResponse getAllTransactionsResponse = mapper.toGetAllResponse(transactionList, paginationDto);

        System.out.println("Got all transactions!!!");
        return getAllTransactionsResponse;
    }

    public TransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = transactionRepo.save(mapper.toEntity(createTransactionRequest));
        System.out.println("Transaction created!!!");
        return mapper.toResponse(transaction);
    }

    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepo.findById(id).get();
        System.out.println("Transaction found!!!");
        return mapper.toResponse(transaction);
    }

    public TransactionResponse updateTransaction(Long id, UpdateTransactionRequest updateTransactionRequest) {
        Transaction transaction = transactionRepo.findById(id).get();

        if (!transaction.getAmount().equals(updateTransactionRequest.amount()))
            transaction.setAmount(updateTransactionRequest.amount());
        if (!transaction.getDescription().equals(updateTransactionRequest.description()))
            transaction.setDescription(updateTransactionRequest.description());
        if (!transaction.getCategoryId().equals(updateTransactionRequest.categoryId()))
            transaction.setCategoryId(updateTransactionRequest.categoryId());
        if (!transaction.getTags().equals(updateTransactionRequest.tags()))
            transaction.setTags(updateTransactionRequest.tags());
        if (!transaction.getNotes().equals(updateTransactionRequest.notes()))
            transaction.setNotes(updateTransactionRequest.notes());

        System.out.println("Transaction updated!!!");
        return mapper.toResponse(transactionRepo.save(transaction));
    }

    public void deleteTransaction(Long id) {
        transactionRepo.deleteById(id);
        System.out.println("Transaction deleted!!!");
    }


    public TransactionsListResponse searchTransactions(Optional<Long> category,
                                     Optional<Instant> dateFrom,
                                     Optional<Instant> dateTo,
                                     Optional<BigDecimal> amount,
                                     Optional<String> description) {

        List<Transaction> transactionList = transactionRepo.search(
                category.get(),
                dateFrom.get(),
                dateTo.get(),
                amount.get(),
                description.get());

        System.out.println("Search completed");
        return mapper.toListResponse(transactionList);
    }

    public TransactionsListResponse transfer(TransferRequest transferRequest) {

        if (transferRequest.fromAccountId().equals(transferRequest.toAccountId()))
            throw new IllegalArgumentException("Cannot transfer to the same account");
        if (transferRequest.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        AccountDto from = accountInterface.getAccountById(transferRequest.fromAccountId()).getBody().data();
        AccountDto to = accountInterface.getAccountById(transferRequest.toAccountId()).getBody().data();

        if (from.getBalance().compareTo(transferRequest.amount()) < 0)
            throw new InsufficientFundsException("Not enough funds in account " + transferRequest.fromAccountId());

        Instant now = Instant.now();

        Transaction debitTransaction = Transaction
                .builder()
                .accountId(from.getId())
                .amount(transferRequest.amount().negate())
                .description(transferRequest.description())
                .type(TransactionType.TRANSFER)
                .date(now)
                .status(TransactionStatus.APPROVED)
                .build();

        Transaction creditTransaction =  Transaction
                .builder()
                .accountId(to.getId())
                .amount(transferRequest.amount())
                .description(transferRequest.description())
                .type(TransactionType.TRANSFER)
                .date(now)
                .status(TransactionStatus.APPROVED)
                .build();

        transactionRepo.save(debitTransaction);
        transactionRepo.save(creditTransaction);

        BalanceAdjustmentRequest fromAdjustBalance = new BalanceAdjustmentRequest(from.getBalance().subtract(transferRequest.amount()), transferRequest.description());
        BalanceAdjustmentRequest toAdjustBalance = new BalanceAdjustmentRequest(to.getBalance().add(transferRequest.amount()), transferRequest.description());

        accountInterface.adjustAccountBalance(from.getId(), fromAdjustBalance);
        accountInterface.adjustAccountBalance(to.getId(), toAdjustBalance);

        return mapper.toListResponse(List.of(debitTransaction, creditTransaction));
    }

    public GetAllTransactionsResponse getAllTransactionsByAccountId(Long accountId) {
        int pageIndex = 0;
        int pageSize  = 20;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        var transactions =
                transactionRepo.findTransactionsByAccountId(accountId, pageable);

        PaginationDto paginationDto = PaginationDto.fromPage(transactions);
        List<Transaction> transactionList = transactions.stream().toList();
        GetAllTransactionsResponse getAllTransactionsResponse = mapper.toGetAllResponse(transactionList, paginationDto);

        System.out.println("Got all transactions!!!");
        return getAllTransactionsResponse;
    }

    public TransactionsListResponse split(SplitTransactionRequest splitTransactionRequest) {

        Transaction originalTransaction = transactionRepo.findById(
                splitTransactionRequest.originalTransactionId()).orElseThrow(() -> new EntityNotFoundException(
                "Transaction not found: " + splitTransactionRequest.originalTransactionId()));

        BigDecimal totalSplit = splitTransactionRequest.splits().stream()
                .map(SplitDetailDto::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalSplit.compareTo(originalTransaction.getAmount()) != 0)
            throw new IllegalArgumentException(
                    "Sum of splits (" + totalSplit +
                            ") must equal original amount (" + originalTransaction.getAmount() + ")");

        transactionRepo.delete(originalTransaction);

        List<Transaction> splits = splitTransactionRequest.splits().stream()
                .map(detail -> Transaction.builder()
                        .accountId(originalTransaction.getId())
                        .amount(detail.amount())
                        .categoryId(detail.categoryId())
                        .description(detail.description() != null
                                ? detail.description()
                                : originalTransaction.getDescription())
                        .type(originalTransaction.getType())
                        .tags(originalTransaction.getTags())
                        .notes(originalTransaction.getNotes())
                        .status(originalTransaction.getStatus())
                        .date(Instant.now())
                        .build()
                ).toList();

        transactionRepo.saveAll(splits);

        return new TransactionsListResponse(mapper.toResponseList(splits));
    }

    public TransactionsListResponse getPendingTransactions() {

        List<Transaction> transactionList = transactionRepo.findTransactionsByStatus(TransactionStatus.PENDING);

        return new TransactionsListResponse(mapper.toResponseList(transactionList));
    }

    public TransactionResponse approveTransaction(Long id) {

        Transaction transaction = transactionRepo.findById(id).get();
        transaction.setStatus(TransactionStatus.APPROVED);

        return mapper.toResponse(transaction);
    }

    public SumTransactionsResponse sumTransactions(
            List<Long> categoryIds,
            Instant startDate,
            Instant endDate
    ) {
        BigDecimal sum = transactionRepo.sumByCategoriesAndDateRange(
                categoryIds, startDate, endDate
        );
        return new SumTransactionsResponse(sum);
    }
}
