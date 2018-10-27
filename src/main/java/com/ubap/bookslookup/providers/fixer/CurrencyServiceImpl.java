package com.ubap.bookslookup.providers.fixer;

import com.ubap.bookslookup.providers.fixer.model.Response;
import com.ubap.bookslookup.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final String QUERY_RATES = "http://data.fixer.io/api/latest?access_key=%s";
    private static final List<String> AVAILABLE_CURRENCIES = Arrays.asList("USD", "EUR", "CAD", "PLN");

    private String key;
    private RestTemplate restTemplate;

    @Autowired
    public CurrencyServiceImpl(@Value("${keys.fixerApi}")String key, RestTemplate restTemplate) {
        this.key = key;
        this.restTemplate = restTemplate;
    }

    @Override
    public String defaultCurrency() {
        return AVAILABLE_CURRENCIES.get(0);
    }

    @Override
    public List<String> availableCurrencies() {
        return AVAILABLE_CURRENCIES;
    }

    @Override
    public BigDecimal convertCurrency(String from, String to, BigDecimal val) {
        Response response = requestRates();
        Map<String, BigDecimal> rates = response.getRates();
        BigDecimal baseToFromRate = rates.get(from);
        BigDecimal baseToToRate = rates.get(to);
        BigDecimal fromInBase = val.divide(baseToFromRate, 20, BigDecimal.ROUND_HALF_UP);
        return fromInBase.multiply(baseToToRate);
    }

    private Response requestRates() {
        String url = String.format(QUERY_RATES, this.key);
        ResponseEntity<Response> responseEntity = this.restTemplate.getForEntity(url, Response.class);
        return responseEntity.getBody();
    }
}
