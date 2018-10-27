package com.ubap.bookslookup.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static List<String> AVAILABLE_CURRENCIES = Arrays.asList("USD", "PLN");

    @Override
    public String defaultCurrency() {
        return AVAILABLE_CURRENCIES.get(0);
    }

    @Override
    public List<String> availableCurrencies() {
        return AVAILABLE_CURRENCIES;
    }
}
