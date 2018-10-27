package com.ubap.bookslookup.controllers;

import com.google.common.net.UrlEscapers;
import com.ubap.bookslookup.UserSession;
import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.model.Breadcrumb;
import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.StoreOffer;
import com.ubap.bookslookup.services.CurrencyService;
import com.ubap.bookslookup.services.LibraryService;
import com.ubap.bookslookup.services.OffersCollectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class BookController {

    private UserSession userSession;
    private LibraryService libraryService;
    private OffersCollectorService offersCollectorService;
    private CurrencyService currencyService;

    @Autowired
    public BookController(UserSession userSession, LibraryService libraryService,
                          OffersCollectorService offersCollectorService, CurrencyService currencyService) {
        this.userSession = userSession;
        this.libraryService = libraryService;
        this.offersCollectorService = offersCollectorService;
        this.currencyService = currencyService;
    }

    @RequestMapping({"/search/{query}/book/{isbns}"})
    public String getBookAfterSearchPage(@PathVariable String query, @PathVariable String isbns, Model model) {
        log.debug("Getting getBookAfterSearchPage page for query: {}, isbns: {}", query, isbns);

        int separatorIndex = isbns.indexOf(',');
        Isbn isbn = new Isbn(isbns.substring(0, separatorIndex), isbns.substring(separatorIndex + 1));
        Book book = this.libraryService.searchForBookByIsbn(isbn);

        List<StoreOffer> storeOfferList = this.offersCollectorService.getOffersSortedFromCheapest(book.getIsbn());

        model.addAttribute("availableCurrencies", this.currencyService.availableCurrencies());
        model.addAttribute("currency", this.userSession.getCurrency());
        model.addAttribute("offers", storeOfferList);
        model.addAttribute("book", book);
        String escapedQuery = UrlEscapers.urlFragmentEscaper().escape(query);
        model.addAttribute("breadcrumbs", Arrays.asList(
                new Breadcrumb("/", "Search"),
                new Breadcrumb("/search/" + escapedQuery, query),
                new Breadcrumb("/search/" + escapedQuery + "/book/" + isbns, book.getTitle())));

        return "book";
    }
}
