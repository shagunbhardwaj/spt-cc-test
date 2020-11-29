package com.spt.cctest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.spt.cctest.api.model.CreditCard;

@SpringBootTest
class CctestApplicationTests {

    @Test
    void contextLoads() {
        CreditCard card = new CreditCard();
    }

}
