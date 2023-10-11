package io.sginterview.bankaccountkata.adapters.operation;

import io.sginterview.bankaccountkata.adapters.account.AccountEntity;
import io.sginterview.bankaccountkata.adapters.client.ClientEntity;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Client;
import io.sginterview.bankaccountkata.domain.Operation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OperationMapperTest {

    private final OperationMapper operationMapper = new OperationMapperImpl();

    @Test
    public void testOperationToOperationEntity() {
        UUID operationId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        LocalDateTime localDateTimeMock = LocalDateTime.now();

        // Create a mock ClientEntity object.
        ClientEntity clientEntityMock = new ClientEntity();
        clientEntityMock.setClientId(clientId);
        clientEntityMock.setFirstName("John");
        clientEntityMock.setLastName("Doe");
        clientEntityMock.setEmailAddress("doe@mail.com");

        // Create a mock Account object.
        Account account = new Account(accountId, new Client(clientId, "John", "Doe", "doe@mail.com"), BigDecimal.valueOf(1000.00));

        // Create a mock Operation object.
        Operation operation = new Operation(operationId, account, BigDecimal.valueOf(100.00), localDateTimeMock);


        // Call the operationToOperationEntity() method
        OperationEntity operationEntity = operationMapper.operationToOperationEntity(operation);
        AccountEntity accountEntity = operationEntity.getAccountEntity();

        // Verify that the returned OperationEntity object is correct.
        assertEquals(operationId, operationEntity.getOperationId());
        assertEquals(BigDecimal.valueOf(100.00), operationEntity.getAmount());
        assertEquals(localDateTimeMock, operationEntity.getDate());

        assertEquals(accountId, accountEntity.getAccountId());
        assertEquals(BigDecimal.valueOf(1000.00), accountEntity.getBalance());
        assertEquals(clientEntityMock, accountEntity.getClientEntity());
    }

    @Test
    public void testOperationToOperationEntityWithNullAccount() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime localDateTimeMock = LocalDateTime.now();

        // Create a mock Operation object with a null account property.
        Operation operation = new Operation(operationId, null, BigDecimal.valueOf(100.00), localDateTimeMock);

        // Call the operationToOperationEntity() method
        OperationEntity operationEntity = operationMapper.operationToOperationEntity(operation);

        // Verify that the returned OperationEntity object has a null account property.
        assertEquals(operationId, operationEntity.getOperationId());
        assertEquals(BigDecimal.valueOf(100.00), operationEntity.getAmount());
        assertEquals(localDateTimeMock, operationEntity.getDate());
        assertNull(operationEntity.getAccountEntity());
    }
}