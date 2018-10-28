package com.ubap.bookslookup.services;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import com.ubap.bookslookup.model.StoreOffer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OffersCollectorServiceImplTests {

    private static final String URL_1 = "url1";
    private static final String CURRENCY_1 = "currency1";
    private static final BigDecimal PRICE_1 = new BigDecimal("13.37");
    private static final BigDecimal PRICE_1_AFTER_CONVERSION = new BigDecimal("2.0");
    private static final String STORE_1_LOGO_URL = "logo1";
    private static final String STORE_1_NAME = "store1";

    private static final String URL_2 = "url2";
    private static final String CURRENCY_2 = "currency2";
    private static final BigDecimal PRICE_2 = new BigDecimal("20.12");
    private static final BigDecimal PRICE_2_AFTER_CONVERSION = new BigDecimal("1.0");
    private static final String STORE_2_LOGO_URL = "logo2";
    private static final String STORE_2_NAME = "store2";

    private static final String CURRENCY_3 = "currency3";

    @Mock
    private ShopService shopService1;

    @Mock
    private ShopService shopService2;

    @Mock
    private CurrencyService currencyService;

    private OffersCollectorService offersCollectorService;

    @Before
    public void setUp() {
        this.offersCollectorService = new OffersCollectorServiceImpl(
                Arrays.asList(this.shopService1, this.shopService2), this.currencyService);
    }

    @Test
    public void getOffersSortedFromCheapest() {
        when(this.shopService1.getCheapestBookOfferByIsbn(any())).thenReturn(new Offer(URL_1, CURRENCY_1, PRICE_1));
        when(this.shopService1.getStoreLogoUrl()).thenReturn(STORE_1_LOGO_URL);
        when(this.shopService1.getStoreName()).thenReturn(STORE_1_NAME);

        when(this.shopService2.getCheapestBookOfferByIsbn(any())).thenReturn(new Offer(URL_2, CURRENCY_2, PRICE_2));
        when(this.shopService2.getStoreLogoUrl()).thenReturn(STORE_2_LOGO_URL);
        when(this.shopService2.getStoreName()).thenReturn(STORE_2_NAME);

        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put(CURRENCY_1, new BigDecimal("0.5"));
        rates.put(CURRENCY_2, new BigDecimal("2"));
        when(this.currencyService.getRates()).thenReturn(rates);
        when(this.currencyService.convertCurrency(any(), any(), any(), any())).thenAnswer(
                (Answer<BigDecimal>) invocation -> {
                    String from = invocation.getArgument(0);
                    String to = invocation.getArgument(1);
                    BigDecimal val = invocation.getArgument(2);
                    assertEquals(rates, invocation.getArgument(3));

                    if (CURRENCY_1.equals(from) && CURRENCY_3.equals(to) && PRICE_1.equals(val)) {
                        return new BigDecimal("2.0");
                    } else if (CURRENCY_2.equals(from) && CURRENCY_3.equals(to) && PRICE_2.equals(val)) {
                        return new BigDecimal("1.0");
                    } else {
                        throw new Exception("unexpected call to convertCurrency");
                    }
                }
        );

        List<StoreOffer> storeOfferList =
                this.offersCollectorService.getOffersSortedFromCheapest(new Isbn("5040223005", null), CURRENCY_3);

        assertEquals(2, storeOfferList.size());

        assertEquals(STORE_2_LOGO_URL, storeOfferList.get(0).getStoreLogoUrl());
        assertEquals(STORE_2_NAME, storeOfferList.get(0).getStoreName());
        assertEquals(URL_2, storeOfferList.get(0).getUrl());
        assertEquals(CURRENCY_3, storeOfferList.get(0).getCurrency());
        assertEquals(PRICE_2_AFTER_CONVERSION, storeOfferList.get(0).getPrice());

        assertEquals(STORE_1_LOGO_URL, storeOfferList.get(1).getStoreLogoUrl());
        assertEquals(STORE_1_NAME, storeOfferList.get(1).getStoreName());
        assertEquals(URL_1, storeOfferList.get(1).getUrl());
        assertEquals(CURRENCY_3, storeOfferList.get(1).getCurrency());
        assertEquals(PRICE_1_AFTER_CONVERSION, storeOfferList.get(1).getPrice());
    }
}
