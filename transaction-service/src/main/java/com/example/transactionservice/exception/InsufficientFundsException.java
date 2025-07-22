package com.example.transactionservice.exception;

/**
 * Thrown when an account does not have enough balance for a debit operation.
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
