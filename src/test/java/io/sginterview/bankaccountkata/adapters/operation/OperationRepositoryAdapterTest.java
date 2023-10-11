package io.sginterview.bankaccountkata.adapters.operation;

import io.sginterview.bankaccountkata.adapters.account.AccountEntity;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationRepositoryAdapterTest {

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private OperationMapper operationMapper;

    @InjectMocks
    private OperationRepositoryAdapter operationRepositoryAdapter;

    @Test
    public void testSaveOperation() {
        // Given
        Operation operation = new Operation(UUID.randomUUID(), null, new BigDecimal(100.00), LocalDateTime.now());

        // When
        operationRepositoryAdapter.saveOperation(operation);

        // Then
        verify(operationRepository).save(operationMapper.operationToOperationEntity(operation));
    }

    @Test
    public void testGetOperationsByAccount() {
        // Given
        Account account = new Account(UUID.randomUUID(), null, new BigDecimal(100.00));
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountId(UUID.randomUUID());

        OperationEntity operationEntity1 = new OperationEntity();
        operationEntity1.setOperationId(UUID.randomUUID());
        operationEntity1.setAccountEntity(accountEntity);
        operationEntity1.setAmount(new BigDecimal(100.00));
        operationEntity1.setDate(LocalDateTime.now());

        OperationEntity operationEntity2 = new OperationEntity();
        operationEntity2.setOperationId(UUID.randomUUID());
        operationEntity2.setAccountEntity(accountEntity);
        operationEntity2.setAmount(new BigDecimal(200.00));
        operationEntity2.setDate(LocalDateTime.now());

        when(operationRepository.findOperationEntitiesByAccountEntityAccountId(account.getAccountId())).thenReturn(Set.of(operationEntity1, operationEntity2));

        // When
        Set<Operation> operations = operationRepositoryAdapter.getOperationsByAccount(account);

        // Then
        assertEquals(2, operations.size());
        assertTrue(operations.contains(new Operation(operationEntity1.getOperationId(), account, operationEntity1.getAmount(), operationEntity1.getDate())));
        assertTrue(operations.contains(new Operation(operationEntity2.getOperationId(), account, operationEntity2.getAmount(), operationEntity2.getDate())));
    }

    @Test
    public void testGetOperationsByAccountWithNonExistingAccount() {
        // Given
        Account account = new Account(UUID.randomUUID(), null, new BigDecimal(100.00));

        when(operationRepository.findOperationEntitiesByAccountEntityAccountId(account.getAccountId())).thenReturn(Set.of());

        // When
        Set<Operation> operations = operationRepositoryAdapter.getOperationsByAccount(account);

        // Then
        assertTrue(operations.isEmpty());
    }
}