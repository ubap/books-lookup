package com.ubap.bookslookup.providers.fixer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyServiceImplTests {

    @Autowired
    private CurrencyServiceImpl currencyService;

    @Test
    public void test() {
        this.currencyService.convertCurrency("PLN", "USD", new BigDecimal("100"));
    }

}
