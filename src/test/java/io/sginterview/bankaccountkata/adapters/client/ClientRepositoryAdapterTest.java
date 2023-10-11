package io.sginterview.bankaccountkata.adapters.client;

import io.sginterview.bankaccountkata.domain.Client;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientRepositoryAdapterTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientRepositoryAdapter clientRepositoryAdapter;

    @Test
    public void testGetClient() {
        // Given
        UUID clientId = UUID.randomUUID();
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(clientId);
        clientEntity.setFirstName("John");
        clientEntity.setLastName("Doe");
        clientEntity.setEmailAddress("john.doe@example.com");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientEntity));

        // When
        Client client = clientRepositoryAdapter.getClient(clientId);

        // Then
        assertEquals(clientId, client.getClientId());
        assertEquals("John", client.getFirstName());
        assertEquals("Doe", client.getLastName());
        assertEquals("john.doe@example.com", client.getEmailAddress());
    }

    @Test
    public void testGetClientWithNonExistingClient() {
        // Given
        UUID clientId = UUID.randomUUID();

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class, () -> clientRepositoryAdapter.getClient(clientId));

        // Then
        assertEquals("Client not found with id" + clientId, exception.getMessage());
        verify(clientRepository).findById(clientId);
    }
}