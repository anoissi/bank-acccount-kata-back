package io.sginterview.bankaccountkata.application.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositAmount(@NotNull BigDecimal amount) {

}
