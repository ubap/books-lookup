package com.ubap.bookslookup.providers.ebay;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceEbayImplTest {

    @Autowired
    private ShopServiceEbayImpl shopServiceEbay;

    @Test
    public void getCheapestBookOfferByIsbn() {
        Offer offer = this.shopServiceEbay.getCheapestBookOfferByIsbn(new Isbn(null, "9780393635447"));
        Assertions.assertNotNull(offer);
    }

}
