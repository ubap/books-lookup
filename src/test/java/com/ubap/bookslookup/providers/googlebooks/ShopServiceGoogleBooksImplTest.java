package com.ubap.bookslookup.providers.googlebooks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import com.ubap.bookslookup.providers.googlebooks.model.Response;
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
public class ShopServiceGoogleBooksImplTest {

    private static final String KEY = "dummy_key";
    private static final String EXPECTED_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:5040223005&key=dummy_key";

    private ShopService shopService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.shopService = new ShopServiceGoogleBooksImpl(KEY, this.restTemplate);
    }

    @Test
    public void getCheapestBookOfferByIsbn() throws Exception {

        String jsonResponse = StreamUtils.copyToString(new ClassPathResource("tests/google_query_by_isbn.json").getInputStream(), Charset.defaultCharset());

        ObjectMapper objectMapper = new ObjectMapper();
        Response response  = objectMapper.readValue(jsonResponse, Response.class);

        Mockito.when(this.restTemplate.getForEntity(any(String.class), any())).thenAnswer(
                invocation -> {
                    String url = invocation.getArgument(0);
                    assertEquals(EXPECTED_URL, url);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                });

        Offer offer = this.shopService.getCheapestBookOfferByIsbn(new Isbn("5040223005", null));
        assertEquals("PLN", offer.getCurrency());
        assertEquals("https://play.google.com/store/books/details?id=jA7vDAAAQBAJ&rdid=book-jA7vDAAAQBAJ&rdot=1&source=gbs_api",
                offer.getUrl());
        assertEquals(0, new BigDecimal("1.78").compareTo(offer.getPrice()));
    }

}
