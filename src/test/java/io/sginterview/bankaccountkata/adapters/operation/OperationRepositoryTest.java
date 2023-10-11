package io.sginterview.bankaccountkata.adapters.operation;

import io.sginterview.bankaccountkata.adapters.account.AccountEntity;
import io.sginterview.bankaccountkata.adapters.account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindOperationEntitiesByAccountEntityAccountId() {

        // Given
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountId(UUID.randomUUID());


        accountRepository.save(accountEntity);


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


        operationRepository.save(operationEntity1);
        operationRepository.save(operationEntity2);

        // When
        Set<OperationEntity> operationEntities = operationRepository.findOperationEntitiesByAccountEntityAccountId(accountEntity.getAccountId());

        // Then
        assertEquals(2, operationEntities.size());
        assertTrue(operationEntities.contains(operationEntity1));
        assertTrue(operationEntities.contains(operationEntity2));
    }

    @Test
    public void testFindOperationEntitiesByAccountEntityAccountIdWithNonExistingAccount() {
        // When ( Given : no existing Account)
        Set<OperationEntity> operationEntities = operationRepository.findOperationEntitiesByAccountEntityAccountId(UUID.randomUUID());

        // Then
        assertTrue(operationEntities.isEmpty());
    }
}