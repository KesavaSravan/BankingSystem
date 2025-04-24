package com.example.banking.dto;

public class UserRequest {
    private String username;
    private double amount;
    private String operation; // "deposit" or "withdraw"

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
}
