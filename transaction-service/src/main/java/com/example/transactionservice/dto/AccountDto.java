package com.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String userEmail;
    private String accountName;
    private String type;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private String bankName;
    private String accountNumber;
}
