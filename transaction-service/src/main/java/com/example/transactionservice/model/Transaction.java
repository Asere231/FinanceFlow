package com.example.transactionservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Represents a financial transaction in the system.
 */
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @NotBlank
    @Column(nullable = false)
    private String description;

    /** Owning account ID (references AccountService) */
    @NotNull
    @Column(nullable = false)
    private Long accountId;

    /** Category ID for classification */
    @NotNull
    @Column(nullable = false)
    private Long categoryId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    /** When the transaction occurred */
    @NotNull
    @Column(nullable = false)
    private Instant date;

    /** Optional tags for additional metadata */
    @ElementCollection
    @CollectionTable(
            name = "transaction_tags",
            joinColumns = @JoinColumn(name = "transaction_id")
    )
    @Column(name = "tag")
    private List<String> tags;

    /** Any additional notes */
    @Column(columnDefinition = "TEXT")
    private String notes;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}


