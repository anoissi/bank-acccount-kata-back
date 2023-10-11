package io.sginterview.bankaccountkata.ports.repository;

import io.sginterview.bankaccountkata.domain.Client;

import java.util.UUID;

public interface ClientRepositoryPort {
    /**
     * Gets a client by their ID.
     *
     * @param clientId The ID of the client to be retrieved.
     * @return The client with the specified ID, or null if no such client exists.
     */
    Client getClient(UUID clientId);
}
