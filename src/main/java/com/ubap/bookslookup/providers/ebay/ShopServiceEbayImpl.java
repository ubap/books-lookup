package com.ubap.bookslookup.providers.ebay;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import com.ubap.bookslookup.providers.ebay.model.FindItemsByKeywordsResponse;
import com.ubap.bookslookup.providers.ebay.model.Item;
import com.ubap.bookslookup.providers.ebay.model.Price;
import com.ubap.bookslookup.providers.ebay.model.Response;
import com.ubap.bookslookup.providers.ebay.model.SearchResult;
import com.ubap.bookslookup.providers.ebay.model.SellingStatus;
import com.ubap.bookslookup.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class ShopServiceEbayImpl implements ShopService {

    private static final String STORE_NAME = "Ebay";
    private static final String STORE_LOGO_URL = "https://developer.ebay.com/cms/img/promote/ebay-rightnow-vert.png";
    private static final String QUERY_URL = "http://svcs.ebay.com/services/search/FindingService/v1?GLOBAL-ID=EBAY-US&keywords=%s"
            + "&OPERATION-NAME=findItemsByKeywords&paginationInput.entriesPerPage=25&paginationInput.pageNumber=1&RESPONSE-DATA-FORMAT=json"
            + "&SECURITY-APPNAME=%s&SERVICE-NAME=FindingService&SERVICE-VERSION=1.12.0&sortOrder=PricePlusShippingLowest";

    private String securityAppName;
    private RestTemplate restTemplate;

    @Autowired
    public ShopServiceEbayImpl(@Value("${keys.ebaySecurityAppName}") String securityAppName, RestTemplate restTemplate) {
        this.securityAppName = securityAppName;
        this.restTemplate = restTemplate;

        // the response returned by ebay has MediaType as TEXT_PLAIN, but it is a json actually
        // register message converter for TEXT_PLAIN
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        this.restTemplate.getMessageConverters().add(converter);
    }

    @Override
    public String getStoreLogoUrl() {
        return STORE_LOGO_URL;
    }

    @Override
    public String getStoreName() {
        return STORE_NAME;
    }

    @Cacheable("ebayCheapestBookOfferByIsbn")
    @Override
    public Offer getCheapestBookOfferByIsbn(Isbn isbn) {
        List<Offer> offerList = new ArrayList<>();
        String isbn10 = isbn.getIsbn10();
        String isbn13 = isbn.getIsbn13();
        if (isbn10 != null && !isbn10.isEmpty()) {
            Offer offer = extractOfferFromResponse(queryByIsbn(isbn10));
            if (offer != null) {
                offerList.add(offer);
            }
        }
        if (isbn13 != null && !isbn13.isEmpty()) {
            Offer offer = extractOfferFromResponse(queryByIsbn(isbn13));
            if (offer != null) {
                offerList.add(offer);
            }
        }
        if (offerList.size() == 0) {
            return null;
        }

        offerList.sort(Comparator.comparingInt(Offer::getPrice));
        return offerList.get(0);
    }

    private Response queryByIsbn(String isbn) {
        String url = String.format(QUERY_URL, isbn, this.securityAppName);
        ResponseEntity<Response> response = this.restTemplate.getForEntity(url, Response.class);
        return response.getBody();
    }

    private Offer extractOfferFromResponse(Response response) {
        FindItemsByKeywordsResponse findItemsByKeywordsResponse = response.getFindItemsByKeywordsResponse().get(0);
        SearchResult searchResult = findItemsByKeywordsResponse.getSearchResult().get(0);

        if (searchResult.getCount() == 0) {
            return null;
        }

        Item item = searchResult.getItems().get(0);
        String viewItemUrl = item.getViewItemURL().get(0);
        SellingStatus sellingStatus = item.getSellingStatus().get(0);
        Price currentPrice = sellingStatus.getCurrentPrice().get(0);

        // convert the price to decimal format
        int priceDecimal = (int) (Double.parseDouble(currentPrice.getValue()) * 100.);
        return new Offer(viewItemUrl, currentPrice.getCurrencyId(), priceDecimal);
    }
}
