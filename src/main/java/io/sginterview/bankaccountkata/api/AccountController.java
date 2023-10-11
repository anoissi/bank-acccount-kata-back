package io.sginterview.bankaccountkata.api;

import io.sginterview.bankaccountkata.application.request.DepositAmount;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Operation;
import io.sginterview.bankaccountkata.ports.service.AccountServicePort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    /**
     * The account service port.
     */
    private AccountServicePort accountServicePort;

    /**
     * Constructor of account controller.
     *
     * @param accountServicePort The account service port.
     */
    public AccountController(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    /**
     * Deposits the given amount into the account with the given account ID.
     *
     * @param accountId     The account ID.
     * @param depositAmount The amount to deposit.
     * @return A ResponseEntity with a 200 OK status code and the updated account as the body.
     * @throws IllegalArgumentException If the account ID is null or the deposit amount is less than zero.
     */
    @PatchMapping("/{accountId}/deposit")
    ResponseEntity deposit(@PathVariable UUID accountId, @Valid @RequestBody DepositAmount depositAmount) {
        Account account = accountServicePort.deposit(accountId, depositAmount.amount());
        return ResponseEntity.ok(account);
    }

    /**
     * Gets the account details for the account with the given account ID.
     *
     * @param accountId The account ID.
     * @return A ResponseEntity with a 200 OK status code and the account details as the body.
     * @throws IllegalArgumentException If the account ID is null.
     */
    @GetMapping("/{accountId}")
    ResponseEntity<Account> getAccountDetails(@PathVariable UUID accountId) {
        return ResponseEntity.ok(accountServicePort.getAccount(accountId));
    }

    /**
     * Gets all operations for the account with the given account ID.
     *
     * @param accountId The account ID.
     * @return A ResponseEntity with a 200 OK status code and a set of operations as the body.
     * @throws IllegalArgumentException If the account ID is null.
     */
    @GetMapping("/{accountId}/operations")
    ResponseEntity<Set<Operation>> getAccountOperations(@PathVariable UUID accountId) {
        return ResponseEntity.ok(accountServicePort.getOperations(accountId));
    }
}
