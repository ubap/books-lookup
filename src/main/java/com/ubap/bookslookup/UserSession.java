package com.ubap.bookslookup;

import com.ubap.bookslookup.services.CurrencyService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class UserSession {

    @Setter
    @Getter
    private String currency;

    @Autowired
    public UserSession(CurrencyService currencyService) {
        this.currency = currencyService.defaultCurrency();
    }

}
