package com.ubap.bookslookup.services;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.StoreOffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OffersCollectorServiceImplTests {

    @Autowired
    private OffersCollectorService offersCollectorService;

    @Test
    public void test() {
        List<StoreOffer> storeOfferList =
                this.offersCollectorService.getOffersSortedFromCheapest(new Isbn("5040223005", null));
        assertTrue(storeOfferList.size() > 0);
    }
}
