package io.sginterview.bankaccountkata.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTest {
    @Test
    public void testConstructor() {
        // Given
        UUID operationId = UUID.randomUUID();
        Account account = new Account(UUID.randomUUID(), null, null);
        BigDecimal amount = new BigDecimal(100.00);
        LocalDateTime date = LocalDateTime.now();

        // When
        Operation operation = new Operation(operationId, account, amount, date);

        // Then
        assertEquals(operationId, operation.getOperationId());
        assertEquals(account, operation.getAccount());
        assertEquals(amount, operation.getAmount());
        assertEquals(date, operation.getDate());
    }

    @Test
    public void testHashCode() {
        // Given
        UUID operationId = UUID.randomUUID();
        Operation operation1 = new Operation(operationId, null, new BigDecimal(100.00), LocalDateTime.now());
        Operation operation2 = new Operation(operationId, null, new BigDecimal(100.00), LocalDateTime.now());

        // Then
        assertEquals(operation1.hashCode(), operation2.hashCode());
    }

    @Test
    public void testEquals() {
        // Given
        UUID operationId = UUID.randomUUID();
        Operation operation1 = new Operation(operationId, null, new BigDecimal(100.00), LocalDateTime.now());
        Operation operation2 = new Operation(operationId, null, new BigDecimal(100.00), LocalDateTime.now());

        // Then
        assertEquals(operation1, operation2);
    }

}
