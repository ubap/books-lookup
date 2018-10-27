package com.ubap.bookslookup.providers.googlebooks;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import com.ubap.bookslookup.providers.googlebooks.model.Response;
import com.ubap.bookslookup.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShopServiceGoogleBooksImpl implements ShopService {

    private static final String SERVICE_NAME = "Google Books";
    private static final String SERVICE_LOGO_URL = "https://www.google.pl/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png";

    private String key;
    private RestTemplate restTemplate;

    @Autowired
    public ShopServiceGoogleBooksImpl(@Value("${keys.googleApi}") String key, RestTemplate restTemplate) {
        this.key = key;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getStoreLogoUrl() {
        return SERVICE_LOGO_URL;
    }

    @Override
    public String getStoreName() {
        return SERVICE_NAME;
    }

    @Cacheable("googleBooksCheapestBookOfferByIsbn")
    @Override
    public Offer getCheapestBookOfferByIsbn(Isbn isbn) {
        Offer offer = null;
        String isbn10 = isbn.getIsbn10();
        String isbn13 = isbn.getIsbn13();
        if (isbn10 != null && !isbn10.isEmpty()) {
            Response response = GoogleBooksHelper.queryByIsbn(this.restTemplate, isbn10, this.key);
            offer = GoogleBooksHelper.extractOfferFromResponse(response);
        }
        if (offer == null && isbn13 != null && !isbn13.isEmpty()) {
            Response response = GoogleBooksHelper.queryByIsbn(this.restTemplate, isbn13, this.key);
            offer = GoogleBooksHelper.extractOfferFromResponse(response);
        }
        return offer;
    }

}
