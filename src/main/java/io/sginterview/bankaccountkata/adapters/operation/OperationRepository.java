package io.sginterview.bankaccountkata.adapters.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;


public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {

    /**
     * Finds all operation entities for the given account entity account ID.
     *
     * @param accountId The account entity account ID.
     * @return A set of operation entities for the given account entity account ID.
     */
    Set<OperationEntity> findOperationEntitiesByAccountEntityAccountId(UUID accountId);
}
