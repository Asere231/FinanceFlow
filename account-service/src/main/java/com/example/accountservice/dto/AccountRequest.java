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
public class AccountRequest {

    private String userEmail;
    private String accountName;
    private String type;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private String bankName;
    private String accountNumber;

    /**
     * Convert this DTO into its Account entity.
     */
    public Account toEntity() {
        return new Account(
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
    public static List<Account> toEntityList(List<AccountRequest> accounts) {
        return accounts.stream()
                .map(AccountRequest::toEntity)
                .collect(Collectors.toList());
    }

}
