package com.ubap.bookslookup.providers.googlebooks;

import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.providers.googlebooks.model.IndustryIdentifier;
import com.ubap.bookslookup.providers.googlebooks.model.Item;
import com.ubap.bookslookup.providers.googlebooks.model.Response;
import com.ubap.bookslookup.providers.googlebooks.model.VolumeInfo;
import com.ubap.bookslookup.services.LibraryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleBooksLibraryServiceImpl implements LibraryService {

    private static final String ISBN_10_TYPE = "ISBN_10";
    private static final String ISBN_13_TYPE = "ISBN_13";
    private static final int MAX_RESULTS_PER_PAGE = 40;
    private static final String QUERY_BY_TITLE = "https://www.googleapis.com/books/v1/volumes?maxResults="
            + MAX_RESULTS_PER_PAGE
            + "&startIndex=%d&q=intitle:%s&key=%s&fields=kind,totalItems,items(volumeInfo/title,volumeInfo/subtitle," +
            "volumeInfo/authors,volumeInfo/industryIdentifiers,volumeInfo/imageLinks)";

    @Value("${keys.googleApi}")
    private String key;

    @Override
    public List<Book> searchForBooksWithIsbnByTitle(String title) {
        List<Book> bookList = new ArrayList<>();

        int totalItems = 1;
        for (int startIndex = 0; startIndex < totalItems; startIndex += MAX_RESULTS_PER_PAGE) {
            Response response = queryByTitle(title, startIndex);
            totalItems = response.getTotalItems();
            bookList.addAll(filterBooksWithoutIsbn(extractBooksFromResponse(response)));
        }
        return bookList;
    }

    @Override
    public Book searchForBookByIsbn(String isbn) {
        return null;
    }

    private Response queryByTitle(String title, int startIndex) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(QUERY_BY_TITLE, startIndex, title, key);
        ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
        return response.getBody();
    }

    private List<Book> filterBooksWithoutIsbn(List<Book> bookList) {
        return bookList.stream()
                .filter(book -> book.getIsbn().getIsbn10() != null && book.getIsbn().getIsbn13() != null)
                .collect(Collectors.toList());
    }

    private List<Book> extractBooksFromResponse(Response response) {
        if (response.getItems() == null) {
            return Collections.emptyList();
        }
        return response.getItems()
                .stream()
                .map(GoogleBooksLibraryServiceImpl::bookFromItem)
                .collect(Collectors.toList());
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

        return bookBuilder.build();
    }
}
