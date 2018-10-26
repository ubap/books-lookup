package com.ubap.bookslookup.providers.googlebooks;

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
public class ShopServiceGoogleBooksImplTest {

    @Autowired
    private ShopServiceGoogleBooksImpl shopServiceGoogleBooks;

    @Test
    public void getCheapestBookOfferByIsbn() {
        Offer offer = this.shopServiceGoogleBooks.getCheapestBookOfferByIsbn(new Isbn("5040223005", null));
        Assertions.assertNotNull(offer);
    }

}
