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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceEbayImplTest {

    private static final String SECURITY_APP_KEY = "dummy_key";
    private static final String EXPECTED_URL = "http://svcs.ebay.com/services/search/FindingService/v1?GLOBAL-ID=EBAY-" +
            "US&keywords=9780393635447&OPERATION-NAME=findItemsByKeywords&paginationInput.entriesPerPage=25&pagination" +
            "Input.pageNumber=1&RESPONSE-DATA-FORMAT=json&SECURITY-APPNAME=dummy_key&SERVICE-NAME=FindingService&SERVI" +
            "CE-VERSION=1.12.0&sortOrder=PricePlusShippingLowest";

    private static final String JSON_RESPONSE = "{\"findItemsByKeywordsResponse\":[{\"ack\":[\"Success\"],\"version\":" +
            "[\"1.13.0\"],\"timestamp\":[\"2018-10-27T15:51:13.565Z\"],\"searchResult\":[{\"@count\":\"1\",\"item\":[{" +
            "\"itemId\":[\"282650766481\"],\"title\":[\"Book of African-American Quotations (2012, E-book)\"],\"global" +
            "Id\":[\"EBAY-US\"],\"primaryCategory\":[{\"categoryId\":[\"171243\"],\"categoryName\":[\"Nonfiction\"]}]," +
            "\"galleryURL\":[\"http:\\/\\/thumbs2.ebaystatic.com\\/m\\/mvgewEcs0FFqrPVeupFYsJA\\/140.jpg\"],\"viewItem" +
            "URL\":[\"http:\\/\\/www.ebay.com\\/itm\\/Book-African-American-Quotations-2012-E-book-\\/282650766481\"]," +
            "\"productId\":[{\"@type\":\"ReferenceID\",\"__value__\":\"128395761\"}],\"paymentMethod\":[\"PayPal\"],\"" +
            "autoPay\":[\"false\"],\"postalCode\":[\"91402\"],\"location\":[\"Panorama City,CA,USA\"],\"country\":[\"U" +
            "S\"],\"shippingInfo\":[{\"shippingServiceCost\":[{\"@currencyId\":\"USD\",\"__value__\":\"0.0\"}],\"shipp" +
            "ingType\":[\"Free\"],\"shipToLocations\":[\"Worldwide\"],\"expeditedShipping\":[\"false\"],\"oneDayShippi" +
            "ngAvailable\":[\"false\"],\"handlingTime\":[\"3\"]}],\"sellingStatus\":[{\"currentPrice\":[{\"@currencyId" +
            "\":\"USD\",\"__value__\":\"3.0\"}],\"convertedCurrentPrice\":[{\"@currencyId\":\"USD\",\"__value__\":\"3." +
            "0\"}],\"sellingState\":[\"Active\"],\"timeLeft\":[\"P10DT7H38M30S\"]}],\"listingInfo\":[{\"bestOfferEnabl" +
            "ed\":[\"true\"],\"buyItNowAvailable\":[\"false\"],\"startTime\":[\"2017-09-12T23:30:43.000Z\"],\"endTime\"" +
            ":[\"2018-11-06T23:29:43.000Z\"],\"listingType\":[\"FixedPrice\"],\"gift\":[\"false\"],\"watchCount\":[\"1" +
            "\"]}],\"returnsAccepted\":[\"false\"],\"condition\":[{\"conditionId\":[\"1000\"],\"conditionDisplayName\"" +
            ":[\"Brand New\"]}],\"isMultiVariationListing\":[\"false\"],\"topRatedListing\":[\"false\"]}]}],\"paginati" +
            "onOutput\":[{\"pageNumber\":[\"1\"],\"entriesPerPage\":[\"25\"],\"totalPages\":[\"1\"],\"totalEntries\":[" +
            "\"1\"]}],\"itemSearchURL\":[\"http:\\/\\/www.ebay.com\\/sch\\/i.html?_nkw=0486112446&_ddo=1&_ipg=25&_pgn=" +
            "1&_sop=15\"]}]}";

    private ShopService shopServiceEbay;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.shopServiceEbay = new ShopServiceEbayImpl(SECURITY_APP_KEY, this.restTemplate);
    }

    @Test
    public void getCheapestBookOfferByIsbn() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Response response = objectMapper.readValue(JSON_RESPONSE, Response.class);

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
