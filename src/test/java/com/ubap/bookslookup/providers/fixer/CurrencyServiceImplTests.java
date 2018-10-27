package com.ubap.bookslookup.providers.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubap.bookslookup.providers.fixer.model.Response;
import com.ubap.bookslookup.services.CurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTests {

    private static final String KEY = "dummy_key";

    private CurrencyService currencyService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.currencyService = new CurrencyServiceImpl(KEY, this.restTemplate);
    }

    @Test
    public void convertCurrency() {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("PLN", new BigDecimal(2));
        rates.put("EUR", new BigDecimal(0.5));

        BigDecimal convertedCurrency = this.currencyService.convertCurrency(
                "PLN", "EUR", new BigDecimal(100), rates);

        assertEquals(0, new BigDecimal(25).compareTo(convertedCurrency));
    }

    @Test
    public void getRates() throws Exception {
        String jsonResponse = StreamUtils.copyToString(new ClassPathResource("tests/fixer_currency_rates.json").getInputStream(), Charset.defaultCharset());

        ObjectMapper objectMapper = new ObjectMapper();
        Response response  = objectMapper.readValue(jsonResponse, Response.class);

        Mockito.when(this.restTemplate.getForEntity(any(String.class), any())).thenAnswer(
                invocation -> {
                    String url = invocation.getArgument(0);
                    assertEquals("http://data.fixer.io/api/latest?access_key=dummy_key", url);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                });

        Map<String, BigDecimal> rates = this.currencyService.getRates();

        assertEquals(0, new BigDecimal("4.196251").compareTo(rates.get("AED")));
        assertEquals(0, new BigDecimal("1.142446").compareTo(rates.get("USD")));
        assertEquals(0, new BigDecimal("4.320674").compareTo(rates.get("PLN")));

    }
}
