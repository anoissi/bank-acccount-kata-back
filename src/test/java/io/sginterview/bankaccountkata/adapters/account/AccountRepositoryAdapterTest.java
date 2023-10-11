package io.sginterview.bankaccountkata.adapters.account;

import io.sginterview.bankaccountkata.adapters.client.ClientEntity;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Client;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryAdapterTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountRepositoryAdapter accountRepositoryAdapter;

    @Test
    public void testGetAccount() {
        // Given
        UUID accountId = UUID.randomUUID();

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(UUID.randomUUID());

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountId(accountId);
        accountEntity.setClientEntity(clientEntity);
        accountEntity.setBalance(new BigDecimal(100.00));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        // When
        Account account = accountRepositoryAdapter.getAccount(accountId);

        // Then
        assertEquals(accountId, account.getAccountId());
        assertEquals(clientEntity.getClientId(), account.getClient().getClientId());
        assertEquals(clientEntity.getFirstName(), account.getClient().getFirstName());
        assertEquals(clientEntity.getLastName(), account.getClient().getLastName());
        assertEquals(clientEntity.getEmailAddress(), account.getClient().getEmailAddress());
        assertEquals(accountEntity.getBalance(), account.getBalance());
    }

    @Test
    public void testGetAccountWithNonExistingAccount() {
        // Given
        UUID accountId = UUID.randomUUID();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class, () -> accountRepositoryAdapter.getAccount(accountId));

        // Then
        assertEquals("Account not found with id" + accountId, exception.getMessage());
        verify(accountRepository).findById(accountId);
    }

    @Test
    public void testSaveAccount() {
        // Given
        Account account = new Account(UUID.randomUUID(), null, new BigDecimal(100.00));

        // When
        accountRepositoryAdapter.saveAccount(account);

        // Then
        verify(accountRepository).save(accountMapper.accountToAccountEntity(account));
    }

    @Test
    public void test_getAccountsByClient_shouldReturnAccounts() {
        // Given
        Client client = new Client(UUID.randomUUID(), null, null, null);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(UUID.randomUUID());

        AccountEntity accountEntity1 = new AccountEntity();
        accountEntity1.setAccountId(UUID.randomUUID());
        accountEntity1.setClientEntity(clientEntity);
        accountEntity1.setBalance(new BigDecimal(100.00));

        AccountEntity accountEntity2 = new AccountEntity();
        accountEntity2.setAccountId(UUID.randomUUID());
        accountEntity2.setClientEntity(clientEntity);
        accountEntity2.setBalance(new BigDecimal(200.00));

        when(accountRepository.findAccountEntitiesByClientEntityClientId(client.getClientId())).thenReturn(Set.of(accountEntity1, accountEntity2));

        // When
        Set<Account> accounts = accountRepositoryAdapter.getAccountsByClient(client);

        // Then
        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(new Account(accountEntity1.getAccountId(), client, accountEntity1.getBalance())));
        assertTrue(accounts.contains(new Account(accountEntity2.getAccountId(), client, accountEntity2.getBalance())));
    }

    @Test
    public void testGetAccountsByClientWithEmptyAccounts() {
        // Given
        Client client = new Client(UUID.randomUUID(), null, null, null);

        when(accountRepository.findAccountEntitiesByClientEntityClientId(client.getClientId())).thenReturn(Set.of());

        // When
        Set<Account> accounts = accountRepositoryAdapter.getAccountsByClient(client);

        // Then
        assertTrue(accounts.isEmpty());
    }
}