package com.ubap.bookslookup.controllers;

import com.ubap.bookslookup.model.Breadcrumb;
import com.ubap.bookslookup.services.CurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexControllerTests {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getIndexPage() throws Exception {
        List<Breadcrumb> expectedBreadcrumbs = Arrays.asList(new Breadcrumb("/", "Search"));
        this.mockMvc.perform(get("/index"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("currency", "USD"))
                .andExpect(model().attribute("breadcrumbs", expectedBreadcrumbs))
                .andExpect(model().attribute("availableCurrencies", this.currencyService.availableCurrencies()));

    }
}
