package com.example.accountservice.model;

import com.example.accountservice.dto.AccountResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String userEmail;

    @NotBlank
    @Column(nullable = false)
    private String accountName;

    @NotNull
    @Column(nullable = false, length = 20)
    private String type;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal creditLimit;

    @NotBlank
    @Column(nullable = false)
    private String bankName;

    @NotBlank
    @Column(nullable = false, unique = true, length = 30)
    private String accountNumber;

    public Account(String userEmail, String accountHolderName, String type, BigDecimal balance, BigDecimal creditLimit, String bankName, String accountNumber) {
        this.userEmail = userEmail;
        this.accountName = accountHolderName;
        this.type = type;
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    /**
     * Convert this Account entity into its DTO.
     */
    public AccountResponse toResponse() {
        return new AccountResponse(
                this.userEmail,
                this.accountName,
                this.type,
                this.balance,
                this.creditLimit,
                this.bankName,
                this.accountNumber
        );
    }

    /**
     * Convert a list of Accounts into a list of DTOs.
     */
    public static List<AccountResponse> toResponseList(List<Account> accounts) {
        return accounts.stream()
                .map(Account::toResponse)
                .collect(Collectors.toList());
    }

}
