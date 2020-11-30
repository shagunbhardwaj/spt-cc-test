package com.spt.cctest.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spt.cctest.api.TestDataHelper;
import com.spt.cctest.api.model.CreditCard;
import com.spt.cctest.api.model.Money;
import com.spt.cctest.component.CreditCardService;

@ExtendWith(MockitoExtension.class)
class CreditCardControllerTest {

    private static final String URI_CREDITCARDS = "http://localhost:8080/creditcards";
    private static final String URI_CREATECARD = URI_CREDITCARDS;
    private static final String URI_GETCARDS = URI_CREDITCARDS;

    @Mock
    private CreditCardService creditCardService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CreditCardController creditCardController;
    private CreditCard testCreditCard;
    private CreditCard persistedCreditCard;

    @BeforeEach
    public void setUp() {
        creditCardController = new CreditCardController(creditCardService);
        mockMvc = standaloneSetup(creditCardController)
                .setControllerAdvice(new RestResponseExceptionHandler())
                .build();
        objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .build();

        testCreditCard = TestDataHelper.createValidTestCreditCardApiObject();
        persistedCreditCard = TestDataHelper.createValidTestCreditCardApiObject();
        persistedCreditCard.setId(UUID.randomUUID());
        persistedCreditCard.setCreatedTimestamp(Instant.now());
        persistedCreditCard.setAccountBalance(new Money(BigDecimal.ZERO, "£"));
    }

    @Test
    public void shouldCreateCreditCard() throws Exception {
        when(creditCardService.createCreditCard(any(CreditCard.class))).thenReturn(persistedCreditCard);
        String creditCardRequest = objectMapper.writeValueAsString(testCreditCard);

        MvcResult mvcResult = mockMvc.perform(post(URI_CREATECARD)
                .content(creditCardRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        CreditCard actualCard = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CreditCard.class);
        assertThat(actualCard.getId()).isEqualTo(persistedCreditCard.getId());
        assertThat(actualCard.getCreatedTimestamp()).isEqualTo(persistedCreditCard.getCreatedTimestamp());
        assertThat(actualCard.getAccountBalance().getAmount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void shouldThrowErrorForInvalidCreditCards() throws Exception {
        testCreditCard.setCreditCardNumber(TestDataHelper.TEST_INVALID_CARD_NUMBER);
        testCreditCard.setCardOwnerName("ab");
        testCreditCard.setAccountLimit(new Money(new BigDecimal(-1), "£"));
        String creditCardRequest = objectMapper.writeValueAsString(testCreditCard);

        MvcResult mvcResult = mockMvc.perform(post(URI_CREATECARD)
                .content(creditCardRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();

        Map result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);
        assertThat(result.get("details") instanceof List);
        assertThat(((List)result.get("details")).contains("creditCardNumber: card number must be valid with length 11 - 19 digits"));
        assertThat(((List)result.get("details")).contains("cardOwnerName: size must be between 3 and 255"));
        assertThat(((List)result.get("details")).contains("accountLimit: must not be negative"));
    }

    @Test
    public void shouldReturnConflictForDuplicateCardNumber() throws Exception {
        when(creditCardService.createCreditCard(any(CreditCard.class)))
                .thenThrow(new DataIntegrityViolationException("violation"));
        String creditCardRequest = objectMapper.writeValueAsString(testCreditCard);
        mockMvc.perform(post(URI_CREATECARD)
                .content(creditCardRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldFetchExistingCreditCards() throws Exception {
        when(creditCardService.getAllCreditCards()).thenReturn(List.of(persistedCreditCard));

        MvcResult mvcResult = mockMvc.perform(get(URI_GETCARDS)
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                            .andExpect(status().isOk())
                            .andReturn();
        List list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        ModelMapper modelMapper = new ModelMapper();
        CreditCard actualCard = modelMapper.map(list.get(0), CreditCard.class);
        assertThat(actualCard.equals(persistedCreditCard));
    }

}