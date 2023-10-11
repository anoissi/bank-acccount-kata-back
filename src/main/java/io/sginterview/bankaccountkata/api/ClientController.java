package io.sginterview.bankaccountkata.api;

import io.sginterview.bankaccountkata.application.ClientService;
import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.ports.service.AccountServicePort;
import io.sginterview.bankaccountkata.ports.service.ClientServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    /**
     * The client service port.
     */
    private final ClientServicePort clientService;

    /**
     * Constructor of client controller.
     *
     * @param clientService The client service port.
     */
    public ClientController(ClientServicePort clientService) {
        this.clientService = clientService;
    }

    /**
     * Gets all accounts for the client with the given client ID.
     *
     * @param clientId The client ID.
     * @return A ResponseEntity with a 200 OK status code and a set of accounts as the body.
     * @throws IllegalArgumentException If the client ID is null.
     */
    @GetMapping("/{clientId}/accounts")
    ResponseEntity<Set<Account>> getClientAccounts(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.getAccounts(clientId));
    }
}
