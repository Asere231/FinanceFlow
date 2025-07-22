package com.example.budgetservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BudgetPeriod period;

    /** Optional set of category IDs this budget applies to */
    @ElementCollection
    @CollectionTable(name = "budget_categories", joinColumns = @JoinColumn(name = "budget_id"))
    @Column(name = "category_id")
    private Set<Long> categoryIds;

    /** Optional start/end dates (inclusive) */
    private LocalDate startDate;
    private LocalDate endDate;

    /** Percentage threshold at which to generate alerts (0–100) */
    @Min(0) @Max(100)
    private Integer alertThreshold;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
