package io.sginterview.bankaccountkata.application;

import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Operation;
import io.sginterview.bankaccountkata.ports.repository.AccountRepositoryPort;
import io.sginterview.bankaccountkata.ports.service.AccountServicePort;
import io.sginterview.bankaccountkata.ports.repository.OperationRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class AccountService implements AccountServicePort {

    private final AccountRepositoryPort accountRepositoryPort;

    private final OperationRepositoryPort operationRepositoryPort;

    public AccountService(AccountRepositoryPort accountRepositoryPort, OperationRepositoryPort operationRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.operationRepositoryPort = operationRepositoryPort;
    }

    /**
     * Deposits a given amount of money into the account with the specified ID.
     *
     * @param accountId The ID of the account to deposit money into.
     * @param amount The amount of money to deposit.
     * @return The updated account object.
     */
    @Transactional
    public Account deposit(UUID accountId, BigDecimal amount) {
        Account account = accountRepositoryPort.getAccount(accountId);
        account.deposit(amount);
        operationRepositoryPort.saveOperation(new Operation(null, account, amount, LocalDateTime.now()));
        accountRepositoryPort.saveAccount(account);
        return account;
    }

    /**
     * Gets the account with the specified ID.
     *
     * @param accountId The ID of the account to retrieve.
     * @return The account object with the specified ID, or null if no such account exists.
     */
    public Account getAccount(UUID accountId) {
        return accountRepositoryPort.getAccount(accountId);
    }

    /**
     * Gets all operations for the account with the specified ID.
     *
     * @param accountId The ID of the account whose operations are to be retrieved.
     * @return A set of all operations for the specified account.
     */
    public Set<Operation> getOperations(UUID accountId) {
        Account account = getAccount(accountId);
        return operationRepositoryPort.getOperationsByAccount(account);
    }

}