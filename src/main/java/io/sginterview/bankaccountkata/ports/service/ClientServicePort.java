package io.sginterview.bankaccountkata.ports.service;

import io.sginterview.bankaccountkata.domain.Account;

import java.util.Set;
import java.util.UUID;

public interface ClientServicePort {
    /**
     * Gets all accounts for the client with the specified ID.
     *
     * @param clientId The ID of the client whose accounts are to be retrieved.
     * @return A set of all accounts for the specified client.
     */
    Set<Account> getAccounts(UUID clientId);
}
