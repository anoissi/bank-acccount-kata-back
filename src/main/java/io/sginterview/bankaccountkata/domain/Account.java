package io.sginterview.bankaccountkata.domain;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    /**
     * The unique identifier of the account.
     */
    @EqualsAndHashCode.Include
    private UUID accountId;

    /**
     * The client who owns the account.
     */
    @NotNull
    private Client client;

    /**
     * The current balance of the account.
     */
    @NotNull
    private BigDecimal balance;


    /**
     * Constructor of account with the given account ID, client, and balance.
     *
     * @param accountId The account ID.
     * @param client    The client who owns the account.
     * @param balance   The current balance of the account.
     */
    public Account(UUID accountId, Client client, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
        this.client = client;
    }

    /**
     * Deposits the given amount into the account.
     *
     * @param amount The amount to deposit.
     * @throws IllegalArgumentException If the amount is null or negative.
     */
    public void deposit(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.balance = balance.add(amount);
    }
}
