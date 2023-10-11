package io.sginterview.bankaccountkata.adapters.operation;

import io.sginterview.bankaccountkata.domain.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OperationMapper {

    /**
     * Converts an Operation object to an OperationEntity object, mapping the `account` property to the `accountEntity` property, and the `account.client` property to the `accountEntity.clientEntity` property.
     *
     * @param operation The Operation object to convert.
     * @return The OperationEntity object converted from the Operation object.
     */
    @Mapping(source = "account", target = "accountEntity")
    @Mapping(source = "account.client", target = "accountEntity.clientEntity")
    OperationEntity operationToOperationEntity(Operation operation);
}
