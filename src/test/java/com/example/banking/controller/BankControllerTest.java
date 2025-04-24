package com.example.banking.controller;

import com.example.banking.dto.UserRequest;
import com.example.banking.model.User;
import com.example.banking.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BankControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BankService bankService;

    @InjectMocks
    private BankController bankController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();
    }

    @Test
    public void testRegister() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testUser");

        User user = new User("testUser", 0.0);
        when(bankService.register("testUser")).thenReturn(user);

        mockMvc.perform(post("/bank/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void testDeposit() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testUser");
        userRequest.setAmount(100.0);

        User user = new User("testUser", 100.0);
        when(bankService.deposit("testUser", 100.0)).thenReturn(user);

        mockMvc.perform(post("/bank/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    @Test
    public void testTransaction_Deposit() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testUser");
        userRequest.setAmount(50.0);
        userRequest.setOperation("deposit");

        User user = new User("testUser", 50.0);
        when(bankService.deposit("testUser", 50.0)).thenReturn(user);

        mockMvc.perform(post("/bank/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(50.0));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/bank/delete")
                        .param("username", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully."));
    }

    @Test
    public void testGetUsersByIds() throws Exception {
        User user1 = new User("user1", 200.0);
        User user2 = new User("user2", 300.0);
        when(bankService.getUsersByIds(List.of(1L, 2L, 3L))).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/bank/users")
                        .param("id1", "1")
                        .param("id2", "2")
                        .param("id3", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }
}
