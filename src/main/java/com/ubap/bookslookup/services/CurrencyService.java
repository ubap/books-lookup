package com.ubap.bookslookup.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CurrencyService {

    String defaultCurrency();

    List<String> availableCurrencies();

    BigDecimal convertCurrency(String from, String to, BigDecimal val, Map<String, BigDecimal> rates);

    Map<String, BigDecimal> getRates();
}
