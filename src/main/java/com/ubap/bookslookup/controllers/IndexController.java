package com.ubap.bookslookup.controllers;

import com.ubap.bookslookup.model.Breadcrumb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Slf4j
@Controller
public class IndexController {

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");
        model.addAttribute("breadcrumbs", Arrays.asList(new Breadcrumb("/", "Search")));
        return "index";
    }
}
