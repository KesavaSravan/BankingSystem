package com.example.banking.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String username, double balance, double requested) {
        super("Insufficient balance for user '" + username + "'. Available: " + balance + ", Requested: " + requested);
    }
}
