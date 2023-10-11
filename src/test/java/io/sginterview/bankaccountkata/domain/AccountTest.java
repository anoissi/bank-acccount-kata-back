package io.sginterview.bankaccountkata.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    public void testConstructor() {
        // Given
        UUID accountId = UUID.randomUUID();
        Client client = new Client(UUID.randomUUID(), "John", "Doe", "john.doe@example.com");
        BigDecimal balance = new BigDecimal(100.00);

        // When
        Account account = new Account(accountId, client, balance);

        // Then
        assertEquals(accountId, account.getAccountId());
        assertEquals(client, account.getClient());
        assertEquals(balance, account.getBalance());
    }

    @Test
    public void testDeposit() {
        // Given
        Account account = new Account(UUID.randomUUID(), null, new BigDecimal(00.00));
        BigDecimal amount = new BigDecimal(100.00);

        // When
        account.deposit(amount);

        // Then
        assertEquals(amount, account.getBalance());
    }

    @Test
    public void testDepositWithNullAmount() {
        // Given
        Account account = new Account(UUID.randomUUID(), null, new BigDecimal(10.00));

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(null));

        // Then
        assertEquals("Amount cannot be null", exception.getMessage());
    }

    @Test
    public void testDepositWithNegativeAmount() {
        // Given
        Account account = new Account(UUID.randomUUID(), null, new BigDecimal(10.00));

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(new BigDecimal(-100.00)));

        // Then
        assertEquals("Amount cannot be negative", exception.getMessage());
    }

    @Test
    public void testEquals() {
        // Given
        Account account1 = new Account(UUID.randomUUID(), null, null);
        Account account2 = new Account(UUID.randomUUID(), null, null);

        // Then
        assertEquals(account1, account1);
        assertNotEquals(account1, account2);
    }

    @Test
    public void testHashCode() {
        // Given
        Account account1 = new Account(UUID.randomUUID(), null, null);
        Account account2 = new Account(UUID.randomUUID(), null, null);

        // Then
        assertEquals(account1.hashCode(), account1.hashCode());
        assertNotEquals(account1.hashCode(), account2.hashCode());
    }
}