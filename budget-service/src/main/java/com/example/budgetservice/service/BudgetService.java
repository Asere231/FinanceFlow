package com.example.budgetservice.service;

import com.example.budgetservice.dto.*;
import com.example.budgetservice.feign.TransactionInterface;
import com.example.budgetservice.mapper.BudgetMapper;
import com.example.budgetservice.model.Budget;
import com.example.budgetservice.repo.BudgetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.example.budgetservice.model.BudgetPeriod.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetService {

    private final BudgetRepo budgetRepo;
    private final BudgetMapper mapper;
    private final TransactionInterface transactionInterface;

    public BudgetsListResponse getAllBudgets() {
        List<Budget> budgets = budgetRepo.findAll();
        return mapper.toListResponse(budgets);
    }

    public SingleBudgetResponse createBudget(CreateBudgetRequest createBudgetRequest) {
        Budget budget = budgetRepo.save(mapper.toEntity(createBudgetRequest));
        return mapper.toSingleResponse(budgetRepo.save(budget));
    }

    public SingleBudgetResponse getBudgetById(Long id) {
        Budget budget = budgetRepo.findById(id).get();
        return mapper.toSingleResponse(budgetRepo.save(budget));
    }

    public SingleBudgetResponse updateBudget(Long id, UpdateBudgetRequest updateBudgetRequest) {
        Budget budget = budgetRepo.findById(id).get();
        mapper.updateEntity(updateBudgetRequest, budget);

        return mapper.toSingleResponse(budgetRepo.save(budget));
    }

    public void deleteBudget(Long id) {
        budgetRepo.deleteById(id);
    }


    public BudgetsListResponse getCurrentBudgets() {
        LocalDate today = LocalDate.now();
        List<Budget> budgets = budgetRepo.findCurrentBudgets(today);

        return mapper.toListResponse(budgets);
    }

    public BudgetProgressResponse getBudgetProgress(Long id) {
        Budget budget = budgetRepo.findById(id).get();

        // Turn the Set<Long> into a List<Long>
        List<Long> categoryIds = new ArrayList<>(budget.getCategoryIds());

        // Convert LocalDate → Instant
        ZoneId zone = ZoneId.systemDefault();  // or ZoneId.of("America/Detroit")
        Instant startInstant = budget.getStartDate()
                .atStartOfDay(zone)
                .toInstant();
        Instant endInstant = budget.getEndDate()
                .atTime(LocalTime.MAX)          // 23:59:59.999999999
                .atZone(zone)
                .toInstant();

        ResponseEntity<ApiResponse<SumTransactionsResponse>> response = transactionInterface.sumTransactions(
                categoryIds,
                startInstant,
                endInstant
        );

        BigDecimal budgetAmt = budget.getAmount();
        BigDecimal spent = response.getBody().data().sum();
        BigDecimal remaining = budgetAmt.subtract(spent);
        double percentage = spent.divide(budgetAmt, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100L))
                .doubleValue();

        return new BudgetProgressResponse(new BudgetProgressDto(spent, remaining, percentage));
    }

    public SingleBudgetResponse resetBudget(Long id) {
        Budget budget = budgetRepo.findById(id).get();


        LocalDate oldStart = budget.getStartDate();
        LocalDate oldEnd   = budget.getEndDate();

        switch (budget.getPeriod()) {
            case WEEKLY -> {
                // shift forward by 1 week
                budget.setStartDate(oldStart.plusWeeks(1));
                budget.setEndDate(oldEnd.plusWeeks(1));
            }
            case MONTHLY -> {
                // shift forward by 1 month, preserving day‐of‐month as best you can
                budget.setStartDate(oldStart.plusMonths(1));
                budget.setEndDate(oldEnd.plusMonths(1));
            }
            case YEARLY -> {
                // shift forward by 1 year
                budget.setStartDate(oldStart.plusYears(1));
                budget.setEndDate(oldEnd.plusYears(1));
            }
        }

        return mapper.toSingleResponse(budgetRepo.save(budget));
    }
}
