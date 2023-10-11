package io.sginterview.bankaccountkata.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.ports.service.ClientServicePort;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientServicePort clientServicePort;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetClientAccounts() throws Exception {
        // Given
        UUID clientId = UUID.randomUUID();
        Set<Account> accounts = new HashSet<>();
        accounts.add(new Account(UUID.randomUUID(), null, new BigDecimal(100.00)));

        when(clientServicePort.getAccounts(clientId)).thenReturn(accounts);

        // When
        mockMvc.perform(get("/client/{clientId}/accounts", clientId))

                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(accounts)));
    }

    @Test
    public void testGetClientAccountsWithNonExistingClient() throws Exception {
        // Given
        UUID clientId = UUID.randomUUID();

        when(clientServicePort.getAccounts(clientId)).thenThrow(new EntityNotFoundException("Client not found with id" + clientId));

        // When
        mockMvc.perform(get("/client/{clientId}/accounts", clientId))

                // Then
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.errorId").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Internal error occurred, please contact the api team and communicate the error ID"));
    }
}