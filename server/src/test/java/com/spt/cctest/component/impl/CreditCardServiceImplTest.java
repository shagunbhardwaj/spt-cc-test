package com.spt.cctest.component.impl;

import static com.spt.cctest.api.TestDataHelper.TEST_VALID_CARD_BALANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.spt.cctest.api.TestDataHelper;
import com.spt.cctest.api.model.CreditCard;
import com.spt.cctest.component.CreditCardService;
import com.spt.cctest.datasource.model.CreditCardEntity;
import com.spt.cctest.datasource.repository.CreditCardRepository;
import com.spt.cctest.mapper.ServerMapperConfiguration;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

    @Mock
    private CreditCardRepository creditCardRepository;
    private ModelMapper modelMapper;

    private CreditCardService creditCardService;
    private CreditCard testCreditCard;
    private CreditCardEntity cardEntity;

    @BeforeEach
    public void setUp() {
        ServerMapperConfiguration mapperConfiguration = new ServerMapperConfiguration();
        modelMapper = mapperConfiguration.createModelMapperBean();
        creditCardService = new CreditCardServiceImpl(creditCardRepository, modelMapper);
        testCreditCard = TestDataHelper.createValidTestCreditCardApiObject();
        cardEntity = modelMapper.map(testCreditCard, CreditCardEntity.class);
        cardEntity.setValues();
        cardEntity.setId(UUID.randomUUID());
        cardEntity.setAccountBalance(BigDecimal.ZERO);
    }

    @Test
    public void shouldSaveCreditCard() {
        when(creditCardRepository.save(any(CreditCardEntity.class))).thenReturn(cardEntity);
        CreditCard savedCard = creditCardService.createCreditCard(testCreditCard);

        verify(creditCardRepository, times(1)).save(any(CreditCardEntity.class));
        assertThat(savedCard.getId()).isNotNull();
        assertThat(savedCard.getAccountBalance().equals(TEST_VALID_CARD_BALANCE));
        assertThat(savedCard.getCreatedTimestamp()).isNotNull();
    }

    @Test
    public void shouldGetAllCards() {
        when(creditCardRepository.findAll()).thenReturn(List.of(cardEntity));
        List<CreditCard> cards = creditCardService.getAllCreditCards();

        verify(creditCardRepository, times(1)).findAll();
        assertThat(cards).hasSize(1);
    }

}