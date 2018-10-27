package com.ubap.bookslookup.services;

import java.util.List;

public interface CurrencyService {

    String defaultCurrency();
    List<String> availableCurrencies();

}
