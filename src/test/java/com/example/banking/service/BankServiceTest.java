package com.example.banking.service;

import com.example.banking.exception.InsufficientBalanceException;
import com.example.banking.exception.UserNotFoundException;
import com.example.banking.model.User;
import com.example.banking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BankService bankService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User("testUser", 0.0);
    }

    @Test
    public void testRegister() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = bankService.register("testUser");

        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
    }

    @Test
    public void testDeposit() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = bankService.deposit("testUser", 100.0);

        assertEquals(100.0, updatedUser.getBalance());
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        bankService.deleteUser("testUser");

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testGetBalance() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Double balance = bankService.getBalance("testUser");

        assertEquals(0.0, balance);
    }

    @Test
    public void testUserNotFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(
                UserNotFoundException.class,
                () -> bankService.getBalance("testUser")
        );

        assertEquals("User not found: testUser", thrown.getMessage());
    }
}
