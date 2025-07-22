package com.example.accountservice.service;

import com.example.accountservice.dto.*;
import com.example.accountservice.feign.TransactionInterface;
import com.example.accountservice.model.Account;
import com.example.accountservice.repo.AccountRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepo accountRepo;
    private final TransactionInterface transactionInterface;

    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts(Authentication auth) {

        try {
            String email = auth.getName();
            List<Account> accountList = accountRepo.findAccountsByUserEmail(email);
            List<AccountResponse> accountResponseList = Account.toResponseList(accountList);
            ApiResponse<List<AccountResponse>> response =
                    new ApiResponse<>(true, accountResponseList, "Got all accounts for user: " + email);

            System.out.println("Got all accounts for user: " + email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(Authentication auth, AccountRequest accountRequest) {

        try {
            String email = auth.getName();
            accountRequest.setUserEmail(email);
            Account account = accountRepo.save(accountRequest.toEntity());
            AccountResponse accountResponse = account.toResponse();
            ApiResponse<AccountResponse> response =
                    new ApiResponse<>(true, accountResponse, "Account created for user: " + email);

            System.out.println("Account created for user: " + email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(Authentication auth, AccountRequest accountRequest, Long id) {

        try {
            Account account = accountRequest.toEntity();
            account.setUserEmail(auth.getName());
            account.setId(id);

            AccountResponse accountResponse = accountRepo.save(account).toResponse();
            ApiResponse<AccountResponse> response =
                    new ApiResponse<>(true, accountResponse, "Account updated!");

            System.out.println("Account updated!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteAccount(Long id) {

        try {
            accountRepo.deleteById(id);
            ApiResponse<?> response = new ApiResponse<>(true, "{" + id + "} Account deleted!");

            System.out.println("{" + id + "} Account deleted!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<BalanceResponse>> getAccountBalance(Long id) {

        try {
            Account account = accountRepo.findById(id).get();
            BalanceResponse balanceResponse = new BalanceResponse(account.getBalance());
            ApiResponse<BalanceResponse> response = new ApiResponse<>(true, balanceResponse, "Got balance for account: " + id);

            System.out.println("Got balance for account: " + id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<AccountResponse>> adjustAccountBalance(Long id, BigDecimal amount, String reason) {

        try {
            Account account = accountRepo.findById(id).get();
            account.setBalance(amount);

            AccountResponse accountResponse = accountRepo.save(account).toResponse();
            ApiResponse<AccountResponse> response =
                    new ApiResponse<>(true, accountResponse, "Account balance adjusted");

            System.out.println("Reason for adjustment: " + reason);
            System.out.println("Account balance adjusted");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<GetAllTransactionsResponse>> getAccountTransactions(Long id) {
        return transactionInterface.getAllTransactionsByAccountId(id);
    }

    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(Long id) {

        try {
            Account account = accountRepo.findById(id).get();
            AccountResponse accountResponse = account.toResponse();
            System.out.println("Found account");
            return ResponseEntity.ok(
                    new ApiResponse<>(true, accountResponse, "Found account"));
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
