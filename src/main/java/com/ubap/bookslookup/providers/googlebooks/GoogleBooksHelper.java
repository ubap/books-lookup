package com.ubap.bookslookup.providers.googlebooks;

import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import com.ubap.bookslookup.providers.googlebooks.model.IndustryIdentifier;
import com.ubap.bookslookup.providers.googlebooks.model.Item;
import com.ubap.bookslookup.providers.googlebooks.model.Price;
import com.ubap.bookslookup.providers.googlebooks.model.Response;
import com.ubap.bookslookup.providers.googlebooks.model.SaleInfo;
import com.ubap.bookslookup.providers.googlebooks.model.VolumeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
final class GoogleBooksHelper {

    private static final String ISBN_10_TYPE = "ISBN_10";
    private static final String ISBN_13_TYPE = "ISBN_13";
    private static final String QUERY_BY_ISBN = "https://www.googleapis.com/books/v1/volumes?" +
            "q=isbn:%s&key=%s";
    private static final String SALEABILITY_FOR_SALE = "FOR_SALE";


    private GoogleBooksHelper() { }

    static Response queryByIsbn(RestTemplate restTemplate, String isbn, String key) {
        String url = String.format(QUERY_BY_ISBN, isbn, key);
        ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
        return response.getBody();
    }

    static Book getBookByIsbn(RestTemplate restTemplate, String isbn, String key) {
        Response response = queryByIsbn(restTemplate, isbn, key);
        List<Book> bookList = extractBooksFromResponse(response);
        if (bookList.size() == 1) {
            return bookList.get(0);
        } else if (bookList.size() > 1) {
            log.error("Got more than 1 book for isbn: {}", isbn);
        }
        return null;
    }

    static List<Book> extractBooksFromResponse(Response response) {
        if (response.getItems() == null) {
            return Collections.emptyList();
        }
        return response.getItems()
                .stream()
                .map(GoogleBooksHelper::bookFromItem)
                .collect(Collectors.toList());
    }

    static Offer extractOfferFromResponse(Response response) {
        if (response.getTotalItems() == 0) {
            return null;
        }
        Item item = response.getItems().get(0);
        SaleInfo saleInfo = item.getSaleInfo();
        if (!SALEABILITY_FOR_SALE.equals(saleInfo.getSaleability())) {
            log.info("Book not for sale, status: {}", saleInfo.getSaleability());
            return null;
        }
        Price price = saleInfo.getRetailPrice();

        // convert the price to decimal format
        int priceDecimal = Integer.parseInt(price.getAmount().replace(".", ""));

        return new Offer(saleInfo.getBuyLink(), price.getCurrencyCode(), priceDecimal);
    }

    static Book bookFromItem(Item item) {
        VolumeInfo volumeInfo = item.getVolumeInfo();

        String isbn10 = null, isbn13 = null;
        if (volumeInfo.getIndustryIdentifiers() != null) {
            for (IndustryIdentifier industryIdentifier : volumeInfo.getIndustryIdentifiers()) {
                if (ISBN_10_TYPE.equals(industryIdentifier.getType())) {
                    isbn10 = industryIdentifier.getIdentifier();
                } else if (ISBN_13_TYPE.equals(industryIdentifier.getType())) {
                    isbn13 = industryIdentifier.getIdentifier();
                }
                if (isbn10 != null && isbn13 != null) {
                    break;
                }
            }
        }

        Book.BookBuilder bookBuilder = Book.builder();
        bookBuilder
                .title(volumeInfo.getTitle())
                .subtitle(volumeInfo.getSubtitle())
                .authors(volumeInfo.getAuthors())
                .isbn(new Isbn(isbn10, isbn13));

        if (volumeInfo.getImageLinks() != null) {
            bookBuilder.thumbnailUrl(volumeInfo.getImageLinks().getThumbnail());
        }
        if (item.getSaleInfo() != null) {
            bookBuilder.ebook(item.getSaleInfo().getIsEbook());
        }

        return bookBuilder.build();
    }
}
