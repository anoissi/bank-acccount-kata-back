package io.sginterview.bankaccountkata.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sginterview.bankaccountkata.application.request.DepositAmount;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Operation;
import io.sginterview.bankaccountkata.ports.service.AccountServicePort;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServicePort accountServicePort;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testDeposit() throws Exception {
        // Given
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal(100.00);
        DepositAmount depositAmount = new DepositAmount(amount);
        Account account = new Account(accountId, null, amount);


        when(accountServicePort.deposit(accountId, amount)).thenReturn(account);

        // When
        mockMvc.perform(patch("/account/{accountId}/deposit", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositAmount)))

                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(account)));
    }

    @Test
    public void testGetAccountDetails() throws Exception {
        // Given
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal(100.00);
        Account account = new Account(accountId, null, amount);

        when(accountServicePort.getAccount(accountId)).thenReturn(account);

        // When
        mockMvc.perform(get("/account/{accountId}", accountId))

                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(account)));
    }

    @Test
    public void testGetAccountDetailsWithNonExistingAccount() throws Exception {
        // Given
        UUID accountId = UUID.randomUUID();

        when(accountServicePort.getAccount(accountId)).thenThrow(new EntityNotFoundException("Client not found with id" + accountId));

        // When
        mockMvc.perform(get("/account/{accountId}", accountId))

                // Then
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.errorId").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Internal error occurred, please contact the api team and communicate the error ID"));
    }

    @Test
    public void testGetAccountOperations() throws Exception {
        // Given
        UUID accountId = UUID.randomUUID();
        Set<Operation> operations = new HashSet<>();
        operations.add(new Operation(UUID.randomUUID(), null, new BigDecimal(100.00), null));
        operations.add(new Operation(UUID.randomUUID(), null, new BigDecimal(100.00), null));

        when(accountServicePort.getOperations(accountId)).thenReturn(operations);

        // When
        mockMvc.perform(get("/account/{accountId}/operations", accountId))

                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(operations)));
    }

    @Test
    public void testGetAccountOperationsWithNonExistingAccount() throws Exception {
        // Given
        UUID accountId = UUID.randomUUID();

        when(accountServicePort.getOperations(accountId)).thenThrow(new EntityNotFoundException("Client not found with id" + accountId));

        // When
        mockMvc.perform(get("/account/{accountId}/operations", accountId))

                // Then
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.errorId").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Internal error occurred, please contact the api team and communicate the error ID"));
    }
}