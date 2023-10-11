package io.sginterview.bankaccountkata.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {

    @Test
    public void testConstructor() {
        // Given
        UUID clientId = UUID.randomUUID();
        String firstName = "John";
        String lastName = "Doe";
        String emailAddress = "john.doe@example.com";

        // When
        Client client = new Client(clientId, firstName, lastName, emailAddress);

        // Then
        assertEquals(clientId, client.getClientId());
        assertEquals(firstName, client.getFirstName());
        assertEquals(lastName, client.getLastName());
        assertEquals(emailAddress, client.getEmailAddress());
    }


    @Test
    public void testHashCode() {
        // Given
        UUID clientId = UUID.randomUUID();
        Client client1 = new Client(clientId, "John", "Doe", "john.doe@example.com");
        Client client2 = new Client(clientId, "John", "Doe", "john.doe@example.com");

        // Then
        assertEquals(client1.hashCode(), client2.hashCode());
    }

    @Test
    public void testEquals() {
        // Given
        UUID clientId = UUID.randomUUID();
        Client client1 = new Client(clientId, "John", "Doe", "john.doe@example.com");
        Client client2 = new Client(clientId, "John", "Doe", "john.doe@example.com");

        // Then
        assertEquals(client1, client2);
    }
}