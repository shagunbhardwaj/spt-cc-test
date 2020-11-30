package com.spt.cctest.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

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

        assertThat(violations.size()).isEqualTo(5);
    }

    @ParameterizedTest
    @CsvSource({
            "4716305855684825 , 0"
            , "6011007516194890 , 0"
            , "30593746201480 , 0"
            , "4917628529599666 , 0"
            , "5505293798149654 , 0"
            , "3537613693003697 , 0"
            , "36737605517957 , 0"
            , "6387427031121192 , 0"
    })
    public void testForLuhn10ValidCreditCardNumbers(String cardNumber, int expectedErrorCount) {
        CreditCard card = new CreditCard(TEST_CARD_OWNER_NAME, cardNumber, TEST_CARD_LIMIT);
        Set<ConstraintViolation<CreditCard>> violations = validator.validate(card);

        assertThat(violations.size()).isEqualTo(expectedErrorCount);
    }

    @ParameterizedTest
    @CsvSource({
            "4716305855684821 , 1"
            , "6011007516194850 , 1"
            , "30593746201482 , 1"
            , "4917628529599626 , 1"
            , "550529379814964 , 1"
            , "353761369300367 , 1"
            , "36737605517950 , 1"
            , "6387427031121193 , 1"
    })
    public void testForLuhn10InvalidCreditCardNumbers(String cardNumber, int expectedErrorCount) {
        CreditCard card = new CreditCard(TEST_CARD_OWNER_NAME, cardNumber, TEST_CARD_LIMIT);
        Set<ConstraintViolation<CreditCard>> violations = validator.validate(card);

        assertThat(violations.size()).isEqualTo(expectedErrorCount);
    }
}