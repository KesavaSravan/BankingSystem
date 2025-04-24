package com.example.banking.service;

import com.example.banking.exception.InsufficientBalanceException;
import com.example.banking.exception.UserNotFoundException;
import com.example.banking.model.User;
import com.example.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class BankService {

    private static final Logger logger = LoggerFactory.getLogger(BankService.class);

    @Autowired
    private UserRepository userRepository;

    public User register(String username) {
        logger.info("Entering into register method");
        if (username == null || username.isBlank()) {
            logger.warn("Username is null or empty");
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        User user = new User(username, 0.0);
        User savedUser = userRepository.save(user);
        logger.info("User registered successfully: {}", savedUser);
        return savedUser;
    }

    public Double getBalance(String username) {
        logger.info("Entering into balance method");
        logger.info("Fetching balance for user: {}", username);

        return userRepository.findByUsername(username)
                .map(user -> {
                    logger.info("Balance retrieved for user {}: {}", username, user.getBalance());
                    return user.getBalance();
                })
                .orElseThrow(() -> {
                    logger.error("User not found: {}", username);
                    return new UserNotFoundException(username);
                });
    }

    public User deposit(String username, double amount) {
        logger.info("Entering into deposit method");
        logger.info("Depositing {} to user {}", amount, username);

        if (amount <= 0) {
            logger.warn("Invalid deposit amount: {}", amount);
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found for deposit: {}", username);
                    return new UserNotFoundException(username);
                });

        user.setBalance(user.getBalance() + amount);
        User updatedUser = userRepository.save(user);
        logger.info("Deposit successful. New balance: {}", updatedUser.getBalance());
        return updatedUser;
    }

    public User withdraw(String username, double amount) {
        logger.info("Entering into withdraw method");
        logger.info("Withdrawing {} from user {}", amount, username);

        if (amount <= 0) {
            logger.warn("Invalid withdrawal amount: {}", amount);
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found for withdrawal: {}", username);
                    return new UserNotFoundException(username);
                });

        if (user.getBalance() < amount) {
            logger.warn("Insufficient balance. Available: {}, Requested: {}", user.getBalance(), amount);
            throw new InsufficientBalanceException(username, user.getBalance(), amount);
        }

        user.setBalance(user.getBalance() - amount);
        User updatedUser = userRepository.save(user);
        logger.info("Withdrawal successful. New balance: {}", updatedUser.getBalance());
        return updatedUser;
    }

    public void deleteUser(String username) {
        logger.info("Entering into deleteUser method");
        logger.info("Deleting user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found for deletion: {}", username);
                    return new UserNotFoundException(username);
                });

        userRepository.delete(user);
        logger.info("User {} deleted successfully", username);
    }

    public List<User> getUsersByIds(List<Long> ids) {
        logger.info("Entering into getUsersByIds method");
        logger.info("Fetching users with IDs: {}", ids);

        if (ids == null || ids.isEmpty()) {
            logger.warn("ID list is null or empty");
            throw new IllegalArgumentException("ID list cannot be null or empty");
        }

        List<User> users = userRepository.findAllById(ids);

        if (users.isEmpty()) {
            logger.warn("No users found for given IDs: {}", ids);
            throw new UserNotFoundException("None of the provided user IDs exist");
        }

        logger.info("Users found: {}", users);
        return users;
    }
}
