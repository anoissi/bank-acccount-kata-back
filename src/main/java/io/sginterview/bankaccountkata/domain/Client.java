package io.sginterview.bankaccountkata.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded  = true)
public class Client {

    /**
     * The unique identifier of the client.
     */
    @EqualsAndHashCode.Include
    private UUID clientId;

    /**
     * The client's first name.
     */
    @NotNull
    private String firstName;

    /**
     * The client's last name.
     */
    @NotNull
    private String lastName;


    /**
     * The client's email address.
     */
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    private String emailAddress;

    /**
     * Create a new client with the given client ID, first name last name, and email address.
     *
     * @param clientId
     * @param firstName
     * @param lastName
     * @param emailAddress
     */
    public Client(UUID clientId, String firstName, String lastName, String emailAddress) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }
}
