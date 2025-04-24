package com.example.banking.controller;

import com.example.banking.dto.UserRequest;
import com.example.banking.model.User;
import com.example.banking.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;
    @Value("${bank.withdraw.limit}")
    private double withdrawLimit;

    @PostMapping("/register")
    public User register(@RequestBody UserRequest request) {
        return bankService.register(request.getUsername());
    }

    @PostMapping("/deposit")
    public User deposit(@RequestBody UserRequest request) {
        return bankService.deposit(request.getUsername(), request.getAmount());
    }

    // ✅ Unified deposit + withdraw endpoint
    @PostMapping("/transaction")
    public User handleTransaction(@RequestBody UserRequest request) {
        String operation = request.getOperation().toLowerCase();

        if ("withdraw".equals(operation) && request.getAmount() > withdrawLimit) {
            throw new IllegalArgumentException("Withdrawal amount exceeds the configured limit of " + withdrawLimit);
        }

        return switch (operation) {
            case "deposit" -> bankService.deposit(request.getUsername(), request.getAmount());
            case "withdraw" -> bankService.withdraw(request.getUsername(), request.getAmount());
            default -> throw new IllegalArgumentException("Invalid operation. Use 'deposit' or 'withdraw'.");
        };
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam String username) {
        bankService.deleteUser(username);
        return "User deleted successfully.";
    }
    @GetMapping("/users")
    public List<User> getUsersByIds(
            @RequestParam Long id1,
            @RequestParam Long id2,
            @RequestParam Long id3) {

        List<Long> ids = List.of(id1, id2, id3);
        return bankService.getUsersByIds(ids);
    }

}
