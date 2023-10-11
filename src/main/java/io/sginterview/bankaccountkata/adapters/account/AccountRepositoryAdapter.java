package io.sginterview.bankaccountkata.adapters.account;

import io.sginterview.bankaccountkata.adapters.client.ClientEntity;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Client;
import io.sginterview.bankaccountkata.ports.repository.AccountRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountRepositoryAdapter(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    /**
     * Gets an account by its ID.
     *
     * @param accountId The ID of the account to be retrieved.
     * @return The account with the specified ID, or null if no such account exists.
     */
    @Override
    public Account getAccount(UUID accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account not found with id" + accountId));
        ClientEntity clientEntity = accountEntity.getClientEntity();
        Client client = new Client(clientEntity.getClientId(), clientEntity.getFirstName(), clientEntity.getLastName(), clientEntity.getEmailAddress());
        return new Account(accountEntity.getAccountId(), client, accountEntity.getBalance());
    }

    /**
     * Saves an account to the repository.
     *
     * @param account The account to be saved.
     */
    @Override
    public void saveAccount(Account account) {
        accountRepository.save(accountMapper.accountToAccountEntity(account));
    }

    /**
     * Gets all accounts for a given client.
     *
     * @param client The client whose accounts are to be retrieved.
     * @return A set of all accounts for the specified client.
     */
    @Override
    public Set<Account> getAccountsByClient(Client client) {
        return accountRepository.findAccountEntitiesByClientEntityClientId(client.getClientId()).stream().map(accountEntity -> new Account(accountEntity.getAccountId(), client, accountEntity.getBalance())).collect(Collectors.toSet());
    }

}