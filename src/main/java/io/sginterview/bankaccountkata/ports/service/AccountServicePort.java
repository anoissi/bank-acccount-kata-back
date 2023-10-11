package io.sginterview.bankaccountkata.ports.service;

import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Operation;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public interface AccountServicePort {

    /**
     * Deposits a given amount of money into the account with the specified ID.
     *
     * @param accountId The ID of the account to deposit money into.
     * @param amount    The amount of money to deposit.
     * @return The updated account object.
     */
    Account deposit(UUID accountId, BigDecimal amount);

    /**
     * Gets the account with the specified ID.
     *
     * @param accountId The ID of the account to retrieve.
     * @return The account object with the specified ID, or null if no such account exists.
     */
    Account getAccount(UUID accountId);

    /**
     * Gets all operations for the account with the specified ID.
     *
     * @param accountId The ID of the account whose operations are to be retrieved.
     * @return A set of all operations for the specified account.
     */
    Set<Operation> getOperations(UUID accountId);
}
