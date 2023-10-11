package io.sginterview.bankaccountkata.application;

import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Client;
import io.sginterview.bankaccountkata.ports.repository.AccountRepositoryPort;
import io.sginterview.bankaccountkata.ports.repository.ClientRepositoryPort;
import io.sginterview.bankaccountkata.ports.service.ClientServicePort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ClientService implements ClientServicePort {

    private final AccountRepositoryPort accountRepositoryPort;

    private final ClientRepositoryPort clientRepositoryPort;

    public ClientService(AccountRepositoryPort accountRepositoryPort, ClientRepositoryPort clientRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.clientRepositoryPort = clientRepositoryPort;
    }

    /**
     * Gets all accounts for the client with the specified ID.
     *
     * @param clientId The ID of the client whose accounts are to be retrieved.
     * @return A set of all accounts for the specified client.
     */
    @Override
    public Set<Account> getAccounts(UUID clientId) {
        Client client = clientRepositoryPort.getClient(clientId);
        return accountRepositoryPort.getAccountsByClient(client);
    }
}
