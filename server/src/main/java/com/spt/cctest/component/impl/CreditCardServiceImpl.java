package com.spt.cctest.component.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spt.cctest.api.model.CreditCard;
import com.spt.cctest.api.model.Money;
import com.spt.cctest.component.CreditCardService;
import com.spt.cctest.datasource.model.CreditCardEntity;
import com.spt.cctest.datasource.repository.CreditCardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides Credit Card CRUD logic
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final ModelMapper modelMapper;

    /**
     *  Creates a new credit card
     * @param creditCard
     * @return credit card with id and creation timestamp
     */
    @Override
    public CreditCard createCreditCard(CreditCard creditCard) {
        creditCard.setId(UUID.randomUUID());
        creditCard.setAccountBalance(new Money(new BigDecimal(0), creditCard.getAccountLimit().getCurrency()));
        CreditCardEntity cardEntity = persist(creditCard);
        creditCard = modelMapper.map(cardEntity, CreditCard.class);
        return creditCard;
    }

    /**
     *  Return list of all Credit Card
     * @return
     */
    @Override
    public List<CreditCard> getAllCreditCards() {

        return creditCardRepository.findAll().stream()
                .map(creditCardEntity -> {
                    return modelMapper.map(creditCardEntity, CreditCard.class);
                })
                .collect(Collectors.toList());
    }

    private CreditCardEntity persist(CreditCard creditCard) {
        log.info("Persisting credit card ...");
        CreditCardEntity creditCardEntity = modelMapper.map(creditCard, CreditCardEntity.class);
        creditCardEntity = creditCardRepository.save(creditCardEntity);
        log.info("Credit card saved");
        return creditCardEntity;
    }
}
