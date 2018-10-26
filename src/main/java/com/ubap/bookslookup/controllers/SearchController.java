package com.ubap.bookslookup.controllers;

import com.ubap.bookslookup.model.Breadcrumb;
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

    private LibraryService libraryService;

    @Autowired
    public SearchController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @RequestMapping({"/search/{query}"})
    public String getSearchPage(@PathVariable String query, Model model) {
        log.debug("Getting Search page for query: {}", query);

        model.addAttribute("books", this.libraryService.searchForBooksWithIsbnByTitle(query));
        model.addAttribute("query", query);
        model.addAttribute("breadcrumbs", Arrays.asList(
                new Breadcrumb("/", "Search"),
                new Breadcrumb("/search/" + query, query)));

        return "index";
    }
}
