package com.example.transactionservice.mapper;

import com.example.transactionservice.dto.*;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.TransactionStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public Transaction toEntity(CreateTransactionRequest req) {
        return Transaction.builder()
                .amount(req.amount())
                .description(req.description())
                .accountId(req.accountId())
                .categoryId(req.categoryId())
                .type(req.type())
                .date(req.date() != null ? req.date() : Instant.now())
                .tags(req.tags())
                .notes(req.notes())
                .status(TransactionStatus.PENDING)
                .build();
    }

    public void updateEntity(UpdateTransactionRequest req, Transaction tx) {
        if (req.amount() != null) tx.setAmount(req.amount());
        if (req.description() != null) tx.setDescription(req.description());
        if (req.categoryId() != null) tx.setCategoryId(req.categoryId());
        if (req.tags() != null) tx.setTags(req.tags());
        if (req.notes() != null) tx.setNotes(req.notes());
    }

    public TransactionResponse toResponse(Transaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getAmount(),
                tx.getDescription(),
                tx.getAccountId(),
                tx.getCategoryId(),
                tx.getType(),
                tx.getDate(),
                tx.getTags(),
                tx.getNotes(),
                tx.getStatus(),
                tx.getCreatedAt(),
                tx.getUpdatedAt()
        );
    }

    public List<TransactionResponse> toResponseList(List<Transaction> txList) {
        return txList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public GetAllTransactionsResponse toGetAllResponse(List<Transaction> txList, PaginationDto page) {
        return new GetAllTransactionsResponse(
                toResponseList(txList),
                page
        );
    }

    public SingleTransactionResponse toSingleResponse(Transaction tx) {
        return new SingleTransactionResponse(toResponse(tx));
    }

    public TransactionsListResponse toListResponse(List<Transaction> txList) {
        return new TransactionsListResponse(toResponseList(txList));
    }
}
