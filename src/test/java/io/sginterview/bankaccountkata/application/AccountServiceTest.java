package io.sginterview.bankaccountkata.application;

import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Operation;
import io.sginterview.bankaccountkata.ports.repository.AccountRepositoryPort;
import io.sginterview.bankaccountkata.ports.repository.OperationRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private OperationRepositoryPort operationRepositoryPort;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testDeposit() {
        // Given
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal(100.00);
        Account account = new Account(accountId, null, new BigDecimal(0.00));

        when(accountRepositoryPort.getAccount(accountId)).thenReturn(account);

        // When
        Account updatedAccount = accountService.deposit(accountId, amount);

        // Then
        assertEquals(new BigDecimal(100.00), updatedAccount.getBalance());
        verify(accountRepositoryPort).getAccount(accountId);
        verify(accountRepositoryPort).saveAccount(updatedAccount);
        verify(operationRepositoryPort).saveOperation(new Operation(null, account, amount, LocalDateTime.now()));
    }

    @Test
    public void testDepositWithNullAmount() {
        // Given
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = null;
        Account account = new Account(accountId, null, new BigDecimal(0.00));

        when(accountRepositoryPort.getAccount(accountId)).thenReturn(account);


        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.deposit(accountId, amount));

        // Then
        assertEquals("Amount cannot be null", exception.getMessage());
    }

    @Test
    public void testDepositWithNegativeAmount() {
        // Given
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal(-100.00);


        Account account = new Account(accountId, null, new BigDecimal(0.00));

        when(accountRepositoryPort.getAccount(accountId)).thenReturn(account);

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.deposit(accountId, amount));

        // Then
        assertEquals("Amount cannot be negative", exception.getMessage());
    }

    @Test
    public void testGetAccount() {
        // Given
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, null, new BigDecimal(0.00));

        when(accountRepositoryPort.getAccount(accountId)).thenReturn(account);

        // When
        Account getAccount = accountService.getAccount(accountId);

        // Then
        assertEquals(account, getAccount);
        verify(accountRepositoryPort).getAccount(accountId);
    }

    @Test
    public void testGetOperations() {
        // Given
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, null, new BigDecimal(0.00));
        Set<Operation> operations = new HashSet<>();
        operations.add(new Operation(null, account, new BigDecimal(100.00), LocalDateTime.now()));

        when(accountRepositoryPort.getAccount(accountId)).thenReturn(account);
        when(operationRepositoryPort.getOperationsByAccount(account)).thenReturn(operations);

        // When
        Set<Operation> getAccountOperations = accountService.getOperations(accountId);

        // Then
        assertEquals(operations, getAccountOperations);
        verify(accountRepositoryPort).getAccount(accountId);
        verify(operationRepositoryPort).getOperationsByAccount(account);
    }
}