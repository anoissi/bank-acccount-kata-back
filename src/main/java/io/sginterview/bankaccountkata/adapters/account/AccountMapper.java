package io.sginterview.bankaccountkata.adapters.account;

import io.sginterview.bankaccountkata.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {

    /**
     * Converts an Account object to an AccountEntity object, mapping the `client` property to the `clientEntity` property.
     *
     * @param account The Account object to convert.
     * @return The AccountEntity object converted from the Account object.
     */
    @Mapping(source = "client", target = "clientEntity")
    AccountEntity accountToAccountEntity(Account account);
}
