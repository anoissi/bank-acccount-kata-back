package io.sginterview.bankaccountkata.adapters.operation;

import io.sginterview.bankaccountkata.domain.Account;
import io.sginterview.bankaccountkata.domain.Operation;
import io.sginterview.bankaccountkata.ports.repository.OperationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OperationRepositoryAdapter implements OperationRepositoryPort {
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    public OperationRepositoryAdapter(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    /**
     * Saves an operation to the repository.
     *
     * @param operation The operation to be saved.
     */
    @Override
    public void saveOperation(Operation operation) {
        operationRepository.save(operationMapper.operationToOperationEntity(operation));
    }

    /**
     * Gets all operations for a given account.
     *
     * @param account The account whose operations are to be retrieved.
     * @return A set of all operations for the specified account.
     */
    @Override
    public Set<Operation> getOperationsByAccount(Account account) {
        return operationRepository.findOperationEntitiesByAccountEntityAccountId(account.getAccountId()).stream()
                .map(operationEntity -> new Operation(operationEntity.getOperationId(), account, operationEntity.getAmount(), operationEntity.getDate()))
                .collect(Collectors.toSet());
    }
}
