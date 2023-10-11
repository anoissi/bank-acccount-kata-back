package io.sginterview.bankaccountkata.adapters.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;


public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    /**
     * Finds all account entities for the given client entity client ID.
     *
     * @param clientId The client entity client ID.
     * @return A set of account entities for the given client entity client ID.
     */
    Set<AccountEntity> findAccountEntitiesByClientEntityClientId(UUID clientId);
}