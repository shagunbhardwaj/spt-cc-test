package com.spt.cctest.api.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize(as = CreditCard.class)
@JsonDeserialize(as = CreditCard.class)
@NoArgsConstructor
public class CreditCard {
    @NotBlank
    @Size(max = 255, min = 3)
    private String cardOwnerName;

    @NotBlank
    @ValidCardNumber
    private String creditCardNumber;

    @ValidLimitAmount
    private Money accountLimit;

    private Money accountBalance;

    private UUID id;
    private Instant createdTimestamp;

    public CreditCard(@NotBlank String cardOwnerName, @NotBlank String creditCardNumber, Money accountLimit) {
        this.cardOwnerName = cardOwnerName;
        this.creditCardNumber = creditCardNumber;
        this.accountLimit = accountLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard card = (CreditCard) o;
        return Objects.equals(cardOwnerName, card.cardOwnerName) &&
                Objects.equals(creditCardNumber, card.creditCardNumber) &&
                Objects.equals(accountLimit, card.accountLimit) &&
                Objects.equals(accountBalance, card.accountBalance) &&
                Objects.equals(id, card.id) &&
                Objects.equals(createdTimestamp, card.createdTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardOwnerName, creditCardNumber, accountLimit, accountBalance, id, createdTimestamp);
    }
}
