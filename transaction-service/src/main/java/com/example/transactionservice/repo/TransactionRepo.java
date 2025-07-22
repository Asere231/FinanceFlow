package com.example.transactionservice.repo;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.TransactionStatus;
import com.example.transactionservice.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    @Query("""
        SELECT t FROM Transaction t
        WHERE (:accountId IS NULL OR t.accountId = :accountId)
          AND (:categoryId IS NULL OR t.categoryId = :categoryId)
          AND (:type IS NULL OR t.type = :type)
          AND (:dateFrom IS NULL OR t.date >= :dateFrom)
          AND (:dateTo IS NULL OR t.date <= :dateTo)
        """
    )
    Page<Transaction> getAllTransactions(
            @Param("accountId") Long accountId,
            @Param("categoryId") Long categoryId,
            @Param("type") TransactionType type,
            @Param("dateFrom") Instant dateFrom,
            @Param("dateTo") Instant dateTo,
            Pageable pageable
    );

    @Query("""
      SELECT t FROM Transaction t
       WHERE (:categoryId IS NULL    OR t.categoryId = :categoryId)
         AND (:dateFrom   IS NULL    OR t.date       >= :dateFrom)
         AND (:dateTo     IS NULL    OR t.date       <= :dateTo)
         AND (:amount     IS NULL    OR t.amount     = :amount)
         AND (:description IS NULL   OR LOWER(t.description) 
                LIKE LOWER(CONCAT('%', :description, '%')))
      """)
    List<Transaction> search(
            @Param("categoryId")  Long categoryId,
            @Param("dateFrom")    Instant dateFrom,
            @Param("dateTo")      Instant dateTo,
            @Param("amount")      BigDecimal amount,
            @Param("description") String description
    );

    Page<Transaction> findTransactionsByAccountId(Long accountId, Pageable pageable);
    List<Transaction> findTransactionsByStatus(TransactionStatus status);

    @Query("""
      SELECT COALESCE(SUM(t.amount), 0) 
      FROM Transaction t
      WHERE t.categoryId IN :categoryIds
        AND t.date >= :startDate
        AND t.date <= :endDate
    """)
    BigDecimal sumByCategoriesAndDateRange(
            @Param("categoryIds") List<Long> categoryIds,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );
}
