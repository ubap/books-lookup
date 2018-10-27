package com.ubap.bookslookup.controllers;

import com.ubap.bookslookup.UserSession;
import com.ubap.bookslookup.model.Breadcrumb;
import com.ubap.bookslookup.services.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Slf4j
@Controller
public class IndexController {

    private UserSession userSession;
    private CurrencyService currencyService;

    @Autowired
    public IndexController(UserSession userSession, CurrencyService currencyService) {
        this.userSession = userSession;
        this.currencyService = currencyService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");
        model.addAttribute("availableCurrencies", this.currencyService.availableCurrencies());
        model.addAttribute("currency", this.userSession.getCurrency());
        model.addAttribute("breadcrumbs", Arrays.asList(new Breadcrumb("/", "Search")));
        return "index";
    }
}
