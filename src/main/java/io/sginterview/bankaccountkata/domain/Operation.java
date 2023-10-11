package io.sginterview.bankaccountkata.domain;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded  = true)
public class Operation {

    /**
     * The unique identifier of the operation.
     */
    @EqualsAndHashCode.Include
    UUID operationId;

    /**
     * The account on which the operation was performed.
     */
    @NotNull
    Account account;

    /**
     * The amount of the operation.
     */
    @NotNull
    BigDecimal amount;

    /**
     * The date and time of the operation.
     */
    @NotNull
    LocalDateTime date;

    /**
     * Constructor of operation with the given operation ID, account, amount, and date.
     *
     * @param operationId The operation ID.
     * @param account The account on which the operation was performed.
     * @param amount The amount of the operation.
     * @param date The date and time of the operation.
     */
    public Operation(UUID operationId, Account account, BigDecimal amount, LocalDateTime date) {
        this.amount = amount;
        this.date = date;
        this.account =account;
        this.operationId = operationId;
    }
}
