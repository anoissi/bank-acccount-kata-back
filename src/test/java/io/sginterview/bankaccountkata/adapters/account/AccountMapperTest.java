package io.sginterview.bankaccountkata.adapters.account;

import io.sginterview.bankaccountkata.adapters.client.ClientEntity;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountMapperTest {


    private AccountMapper accountMapper = new AccountMapperImpl();


    @Test
    public void testAccountToAccountEntity() {
        // Given
        UUID accountId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();


        ClientEntity clientEntityMock = new ClientEntity();
        clientEntityMock.setClientId(clientId);
        clientEntityMock.setFirstName("John");
        clientEntityMock.setLastName("Doe");
        clientEntityMock.setEmailAddress("doe@mail.com");

        Account account = new Account(accountId, new Client(clientId, "John", "Doe", "doe@mail.com"), BigDecimal.valueOf(1000.00));

        // When
        AccountEntity accountEntity = accountMapper.accountToAccountEntity(account);
        ClientEntity clientEntity = accountEntity.getClientEntity();

        // Then
        assertEquals(accountId, accountEntity.getAccountId());
        assertEquals(BigDecimal.valueOf(1000.00), accountEntity.getBalance());

        assertEquals(clientEntityMock, clientEntity);
    }

    @Test
    public void testAccountToAccountEntityWithNullClient() {

        // Given
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, null, BigDecimal.valueOf(1000.00));

        // When
        AccountEntity accountEntity = accountMapper.accountToAccountEntity(account);
        ClientEntity clientEntity = accountEntity.getClientEntity();

        // Then
        assertEquals(accountId, accountEntity.getAccountId());
        assertEquals(BigDecimal.valueOf(1000.00), accountEntity.getBalance());
        assertNull(clientEntity);
    }
}