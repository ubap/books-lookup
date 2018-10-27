package com.ubap.bookslookup.providers.googlebooks;

import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.providers.googlebooks.model.Response;
import com.ubap.bookslookup.services.LibraryService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LibraryServiceGoogleBooksImpl implements LibraryService {

    private static final int MAX_RESULTS_PER_PAGE = 40;
    private static final String QUERY_BY_TITLE = "https://www.googleapis.com/books/v1/volumes?maxResults="
            + MAX_RESULTS_PER_PAGE
            + "&startIndex=%d&q=intitle:%s&key=%s&fields=kind,totalItems,items(volumeInfo/title,volumeInfo/subtitle," +
            "volumeInfo/authors,volumeInfo/industryIdentifiers,volumeInfo/imageLinks)";

    private String key;
    private RestTemplate restTemplate;

    @Autowired
    public LibraryServiceGoogleBooksImpl(@Value("${keys.googleApi}") String key, RestTemplate restTemplate) {
        this.key = key;
        this.restTemplate = restTemplate;
    }

    @Cacheable("googleBooks")
    @Override
    public List<Book> searchForBooksWithIsbnByTitle(@NonNull String title) {
        List<Book> bookList = new ArrayList<>();

        int totalItems = 1;
        for (int startIndex = 0; startIndex < totalItems; startIndex += MAX_RESULTS_PER_PAGE) {
            Response response = queryByTitle(title, startIndex);
            totalItems = response.getTotalItems();
            bookList.addAll(filterBooksWithoutIsbn(extractBooksFromResponse(response)));
        }
        return bookList;
    }

    @Cacheable("googleBook")
    @Override
    public Book searchForBookByIsbn(Isbn isbn) {
        Book book = null;
        String isbn10 = isbn.getIsbn10();
        String isbn13 = isbn.getIsbn13();
        if (isbn10 != null && !isbn10.isEmpty()) {
            book = GoogleBooksHelper.getBookByIsbn(this.restTemplate, isbn10, this.key);
        }
        if (book == null && isbn13 != null && !isbn13.isEmpty()) {
            book = GoogleBooksHelper.getBookByIsbn(this.restTemplate, isbn13, this.key);
        }

        return book;
    }

    private Response queryByTitle(String title, int startIndex) {
        String url = String.format(QUERY_BY_TITLE, startIndex, title, key);
        ResponseEntity<Response> response = this.restTemplate.getForEntity(url, Response.class);
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
                .map(GoogleBooksHelper::bookFromItem)
                .collect(Collectors.toList());
    }
}
