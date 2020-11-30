package com.spt.cctest.datasource.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "CREDIT_CARD",
        uniqueConstraints = @UniqueConstraint(columnNames="CREDIT_CARD_NUMBER")
)
@Setter
@Getter
public class CreditCardEntity {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CARD_OWNER_NAME")
    private String cardOwnerName;

    @Column(name = "CREDIT_CARD_NUMBER")
    private String creditCardNumber;

    @Column(name = "ACCOUNT_LIMIT")
    private BigDecimal accountLimit;

    @Column(name = "ACCOUNT_BALANCE")
    private BigDecimal accountBalance;

    @Column(name = "ACCOUNT_CURRENCY")
    private String currency;

    @Column(name = "CREATED_TIMESTAMP")
    private Instant createdTimestamp;

    @PrePersist
    public void setValues() {
        if (Objects.isNull(createdTimestamp)) {
            createdTimestamp = Instant.now();
        }
    }
}
