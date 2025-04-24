package com.example.banking.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Double balance;

    public User() {}

    public User(String username, Double balance) {
        this.username = username;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public void setId(Long id) {
        this.id = id;
    }
}
