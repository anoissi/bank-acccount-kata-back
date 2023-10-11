package io.sginterview.bankaccountkata.adapters.operation;

import io.sginterview.bankaccountkata.adapters.account.AccountEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "operation")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    UUID operationId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    AccountEntity accountEntity;

    BigDecimal amount;

    LocalDateTime date;
}
