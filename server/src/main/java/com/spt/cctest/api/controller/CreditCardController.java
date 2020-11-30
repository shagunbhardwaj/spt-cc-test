package com.spt.cctest.api.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.spt.cctest.component.CreditCardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/creditcards")
@Validated
@RequiredArgsConstructor
@Slf4j
public class CreditCardController {

    private final CreditCardService creditCardService;

    /**
     * Add a new credit card
     * @param creditCard
     * @return updated credit card with id , timestamp and balance set to 0
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditCard> createCreditCard(@Valid @RequestBody CreditCard creditCard) {
        log.info("Received request for creating card for {}", creditCard.getCardOwnerName());
        creditCard = creditCardService.createCreditCard(creditCard);
        return ResponseEntity.ok(creditCard);
    }

    /**
     * @return Return list of all credit cards
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CreditCard>> getCreditCards() {
        List<CreditCard> cards = creditCardService.getAllCreditCards();
        return ResponseEntity.ok(cards);
    }

}
