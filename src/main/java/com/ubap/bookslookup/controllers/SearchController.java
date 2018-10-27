package com.ubap.bookslookup.controllers;

import com.google.common.net.UrlEscapers;
import com.ubap.bookslookup.UserSession;
import com.ubap.bookslookup.model.Breadcrumb;
import com.ubap.bookslookup.services.CurrencyService;
import com.ubap.bookslookup.services.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Slf4j
@Controller
public class SearchController {

    private UserSession userSession;
    private LibraryService libraryService;
    private CurrencyService currencyService;

    @Autowired
    public SearchController(UserSession userSession, LibraryService libraryService, CurrencyService currencyService) {
        this.userSession = userSession;
        this.libraryService = libraryService;
        this.currencyService = currencyService;
    }

    @RequestMapping({"/search/{query}"})
    public String getSearchPage(@PathVariable String query, Model model) {
        log.debug("Getting Search page for query: {}", query);

        model.addAttribute("availableCurrencies", this.currencyService.availableCurrencies());
        model.addAttribute("currency", this.userSession.getCurrency());
        model.addAttribute("books", this.libraryService.searchForBooksWithIsbnByTitle(query));
        model.addAttribute("query", query);
        String escapedQuery = UrlEscapers.urlFragmentEscaper().escape(query);
        model.addAttribute("breadcrumbs", Arrays.asList(
                new Breadcrumb("/", "Search"),
                new Breadcrumb("/search/" + escapedQuery, query)));

        return "index";
    }
}
