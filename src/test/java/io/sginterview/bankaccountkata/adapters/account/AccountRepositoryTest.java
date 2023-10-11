package io.sginterview.bankaccountkata.adapters.account;

import io.sginterview.bankaccountkata.adapters.client.ClientEntity;
import io.sginterview.bankaccountkata.adapters.client.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testFindAccountEntitiesByClientEntityClientId() {
        // Given
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(UUID.randomUUID());

        clientRepository.save(clientEntity);


        AccountEntity accountEntity1 = new AccountEntity();
        accountEntity1.setAccountId(UUID.randomUUID());
        accountEntity1.setClientEntity(clientEntity);
        accountEntity1.setBalance(new BigDecimal(100.00));

        AccountEntity accountEntity2 = new AccountEntity();
        accountEntity2.setAccountId(UUID.randomUUID());
        accountEntity2.setClientEntity(clientEntity);
        accountEntity2.setBalance(new BigDecimal(200.00));


        accountRepository.save(accountEntity1);
        accountRepository.save(accountEntity2);

        // When
        Set<AccountEntity> accountEntities = accountRepository.findAccountEntitiesByClientEntityClientId(clientEntity.getClientId());

        // Then
        assertEquals(2, accountEntities.size());
        assertTrue(accountEntities.contains(accountEntity1));
        assertTrue(accountEntities.contains(accountEntity2));
    }

    @Test
    public void testFindAccountEntitiesByClientEntityClientIdWithNonExistingClient() {
        // When ( Given : no existing account )
        Set<AccountEntity> accountEntities = accountRepository.findAccountEntitiesByClientEntityClientId(UUID.randomUUID());

        // Then
        assertTrue(accountEntities.isEmpty());
    }
}