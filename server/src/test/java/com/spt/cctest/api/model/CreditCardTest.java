package com.spt.cctest.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.*;

class CreditCardTest {
    private Validator validator;
    private final String TEST_CARD_OWNER_NAME = "Shagun";
    private final Money TEST_CARD_LIMIT = new Money(new BigDecimal(10000), "$");

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldNotAllowBlankAndNegativeValues() {
        CreditCard card = new CreditCard("", "", new Money(new BigDecimal(-1),"£"));
        Set<ConstraintViolation<CreditCard>> violations = validator.validate(card);

        assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    public void shouldNotAllowInValidCardNumbers() {
        CreditCard card = new CreditCard(TEST_CARD_OWNER_NAME, "4485824301144340", TEST_CARD_LIMIT);
        Set<ConstraintViolation<CreditCard>> violations = validator.validate(card);

        assertThat(violations.size()).isEqualTo(1);
    }
}