package io.sginterview.bankaccountkata.adapters.client;

import io.sginterview.bankaccountkata.domain.Client;
import io.sginterview.bankaccountkata.ports.repository.ClientRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientRepository clientRepository;

    public ClientRepositoryAdapter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Gets a client by their ID.
     *
     * @param clientId The ID of the client to be retrieved.
     * @return The client with the specified ID, or null if no such client exists.
     */
    @Override
    public Client getClient(UUID clientId) {
        ClientEntity clientEntity = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client not found with id" + clientId));
        return new Client(clientEntity.getClientId(), clientEntity.getFirstName(), clientEntity.getLastName(), clientEntity.getEmailAddress());
    }
}
