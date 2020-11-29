package com.spt.cctest.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spt.cctest.api.model.CreditCard;
import com.spt.cctest.component.CreditCardComponent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/creditcard")
@Validated
@RequiredArgsConstructor
@Slf4j
public class CreditCardController {

    private final CreditCardComponent creditCardComponent;

    /**
     * Add a new credit card
     * @param creditCard
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditCard> createCreditCard(@Valid @RequestBody CreditCard creditCard) {
        log.info("Starting Credit card create for {}", "cc");
        creditCard = creditCardComponent.createCreditCard(creditCard);
        return ResponseEntity.ok(creditCard);
    }

    /**
     * Return list of all credit cards
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CreditCard>> getCreditCards() {
        List<CreditCard> cards = creditCardComponent.getAllCrediCards();
        return ResponseEntity.ok(cards);
    }

}
