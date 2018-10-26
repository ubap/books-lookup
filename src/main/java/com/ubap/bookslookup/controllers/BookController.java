package com.ubap.bookslookup.controllers;

import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.model.Breadcrumb;
import com.ubap.bookslookup.model.Isbn;
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
public class BookController {

    private LibraryService libraryService;

    @Autowired
    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @RequestMapping({"/search/{query}/book/{isbns}"})
    public String getBookAfterSearchPage(@PathVariable String query, @PathVariable String isbns, Model model) {
        log.debug("Getting getBookAfterSearchPage page for query: {}, isbns: {}", query, isbns);

        int separatorIndex = isbns.indexOf(',');
        Isbn isbn = new Isbn(isbns.substring(0, separatorIndex), isbns.substring(separatorIndex + 1));
        Book book = this.libraryService.searchForBookByIsbn(isbn);

        model.addAttribute("book", book);
        model.addAttribute("breadcrumbs", Arrays.asList(
                new Breadcrumb("/", "Search"),
                new Breadcrumb("/search/" + query, query),
                new Breadcrumb("/search/" + query + "/book/" + isbns, book.getTitle())));

        return "book";
    }
}
