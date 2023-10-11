package io.sginterview.bankaccountkata.ports.repository;

import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Operation;

import java.util.Set;

public interface OperationRepositoryPort {

    /**
     * Saves an operation to the repository.
     *
     * @param operation The operation to be saved.
     */
    void saveOperation(Operation operation);

    /**
     * Gets all operations for a given account.
     *
     * @param account The account whose operations are to be retrieved.
     * @return A set of all operations for the specified account.
     */
    Set<Operation> getOperationsByAccount(Account account);
}
