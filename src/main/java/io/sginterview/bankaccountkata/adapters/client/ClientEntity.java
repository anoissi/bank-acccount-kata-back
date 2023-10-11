package io.sginterview.bankaccountkata.adapters.client;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID clientId;

    private String firstName;

    private String lastName;

    private String emailAddress;
}
