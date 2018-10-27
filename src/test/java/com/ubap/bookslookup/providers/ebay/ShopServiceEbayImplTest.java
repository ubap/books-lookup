package com.ubap.bookslookup.providers.ebay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import com.ubap.bookslookup.providers.ebay.model.Response;
import com.ubap.bookslookup.services.ShopService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceEbayImplTest {

    private static final String SECURITY_APP_KEY = "dummy_key";
    private static final String EXPECTED_URL = "http://svcs.ebay.com/services/search/FindingService/v1?GLOBAL-ID=EBAY-" +
            "US&keywords=9780393635447&OPERATION-NAME=findItemsByKeywords&paginationInput.entriesPerPage=25&pagination" +
            "Input.pageNumber=1&RESPONSE-DATA-FORMAT=json&SECURITY-APPNAME=dummy_key&SERVICE-NAME=FindingService&SERVI" +
            "CE-VERSION=1.12.0&sortOrder=PricePlusShippingLowest";

    private ShopService shopServiceEbay;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.shopServiceEbay = new ShopServiceEbayImpl(SECURITY_APP_KEY, this.restTemplate);
    }

    @Test
    public void getCheapestBookOfferByIsbn() throws Exception {
        String jsonResponse = StreamUtils.copyToString(new ClassPathResource("tests/ebay_query_by_isbn.json").getInputStream(), Charset.defaultCharset());

        ObjectMapper objectMapper = new ObjectMapper();
        Response response = objectMapper.readValue(jsonResponse, Response.class);

        Mockito.when(this.restTemplate.getForEntity(any(String.class), any())).thenAnswer(
                invocation -> {
                    String url = invocation.getArgument(0);
                    assertEquals(EXPECTED_URL, url);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                });

        Offer offer = this.shopServiceEbay.getCheapestBookOfferByIsbn(new Isbn(null, "9780393635447"));

        assertEquals("http://www.ebay.com/itm/Book-African-American-Quotations-2012-E-book-/282650766481", offer.getUrl());
        assertEquals("USD", offer.getCurrency());
        assertEquals(0, new BigDecimal(3).compareTo(offer.getPrice()));
    }

}
