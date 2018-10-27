package com.ubap.bookslookup.controllers;

import com.ubap.bookslookup.UserSession;
import com.ubap.bookslookup.services.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class CurrencyController {

    private UserSession userSession;
    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(UserSession userSession, CurrencyService currencyService) {
        this.userSession = userSession;
        this.currencyService = currencyService;
    }

    @RequestMapping({"/setcurrency"})
    public String setCurrency(@RequestParam(value="src") String src,
                              @RequestParam(value="currency") String currency) {
        log.debug("Set currency {}, src: {}", currency, src);

        if (this.currencyService.availableCurrencies().contains(currency)) {
            this.userSession.setCurrency(currency);
        }

        return "redirect:" + src;
    }
}
