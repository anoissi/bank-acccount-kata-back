package io.sginterview.bankaccountkata.adapters.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
}
