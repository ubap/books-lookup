package com.ubap.bookslookup.controllers;

import com.ubap.bookslookup.model.Breadcrumb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Slf4j
@Controller
public class BookController {

    @RequestMapping({"/search/{query}/book/{isbns}"})
    public String getBookAfterSearchPage(@PathVariable String query, @PathVariable String isbns, Model model) {
        log.debug("Getting getBookAfterSearchPage page for query: {}, isbns: {}", query, isbns);

        model.addAttribute("breadcrumbs", Arrays.asList(
                new Breadcrumb("/", "Search"),
                new Breadcrumb("/search/" + query, query),
                new Breadcrumb("/search/" + query + "/book/" + isbns, "Book title")));

        return "book";
    }
}
