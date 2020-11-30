package com.spt.cctest.api;

import java.math.BigDecimal;

import com.spt.cctest.api.model.CreditCard;
import com.spt.cctest.api.model.Money;

public class TestDataHelper {
    public static final String TEST_CARD_OWNER = "Shagun Bhardwaj";
    public static final String TEST_VALID_CARD_NUMBER = "4485824301144340";
    public static final String TEST_INVALID_CARD_NUMBER = "4485824301144341";
    public static final Money TEST_VALID_CARD_LIMIT = new Money(new BigDecimal(10), "£");
    public static final Money TEST_VALID_CARD_BALANCE = new Money(new BigDecimal(0), "£");


    public static CreditCard createValidTestCreditCardApiObject() {
        CreditCard card = new CreditCard();
        card.setCardOwnerName(TEST_CARD_OWNER);
        card.setCreditCardNumber(TEST_VALID_CARD_NUMBER);
        card.setAccountLimit(TEST_VALID_CARD_LIMIT);
        return card;
    }

}
