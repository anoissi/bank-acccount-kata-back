package io.sginterview.bankaccountkata.application;

import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Client;
import io.sginterview.bankaccountkata.ports.repository.AccountRepositoryPort;
import io.sginterview.bankaccountkata.ports.repository.ClientRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void testGetAccounts() {
        // Given
        UUID clientId = UUID.randomUUID();
        Client client = new Client(clientId, "John", "Doe", "john.doe@example.com");
        Set<Account> accounts = new HashSet<>();
        accounts.add(new Account(UUID.randomUUID(), client, new BigDecimal(100.00)));

        when(clientRepositoryPort.getClient(clientId)).thenReturn(client);
        when(accountRepositoryPort.getAccountsByClient(client)).thenReturn(accounts);

        // When
        Set<Account> clientAccounts = clientService.getAccounts(clientId);

        // Then
        assertEquals(accounts, clientAccounts);
        verify(clientRepositoryPort).getClient(clientId);
        verify(accountRepositoryPort).getAccountsByClient(client);
    }

    @Test
    public void testGetAccountsWithNonExistingClient() {
        // Given
        UUID clientId = UUID.randomUUID();

        when(clientRepositoryPort.getClient(clientId)).thenReturn(null);

        // When
        Set<Account> clientAccounts = clientService.getAccounts(clientId);

        // Then
        assertTrue(clientAccounts.isEmpty());
        verify(clientRepositoryPort).getClient(clientId);
    }
}