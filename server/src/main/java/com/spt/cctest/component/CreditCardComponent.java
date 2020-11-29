package com.spt.cctest.component;

import java.util.List;
import java.util.UUID;

import com.spt.cctest.api.model.CreditCard;
import com.spt.cctest.datasource.model.CreditCardEntity;

public interface CreditCardComponent {
    CreditCard createCreditCard(CreditCard creditCard);
    List<CreditCard> getAllCrediCards();
}
