package io.sginterview.bankaccountkata.ports.repository;

import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Client;


import java.util.Set;
import java.util.UUID;

public interface AccountRepositoryPort {

    /**
     * Gets an account by its ID.
     *
     * @param accountId The ID of the account to be retrieved.
     * @return The account with the specified ID, or null if no such account exists.
     */
    Account getAccount(UUID accountId);

    /**
     * Saves an account to the repository.
     *
     * @param account The account to be saved.
     */
    void saveAccount(Account account);

    /**
     * Gets all accounts for a given client.
     *
     * @param client The client whose accounts are to be retrieved.
     * @return A set of all accounts for the specified client.
     */
    Set<Account> getAccountsByClient(Client client);
}
