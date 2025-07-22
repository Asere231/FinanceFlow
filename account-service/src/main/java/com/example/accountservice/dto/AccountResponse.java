package com.example.accountservice.dto;

import com.example.accountservice.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;
    private String userEmail;
    private String accountName;
    private String type;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private String bankName;
    private String accountNumber;

    public AccountResponse(String userEmail, String accountName, String type, BigDecimal balance, BigDecimal creditLimit, String bankName, String accountNumber) {
        this.userEmail = userEmail;
        this.accountName = accountName;
        this.type = type;
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
}
