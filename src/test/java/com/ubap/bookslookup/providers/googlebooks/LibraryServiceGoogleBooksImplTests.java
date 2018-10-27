package com.ubap.bookslookup.providers.googlebooks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.providers.googlebooks.model.Response;
import com.ubap.bookslookup.services.LibraryService;
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

import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class LibraryServiceGoogleBooksImplTests {

    private static final String KEY = "dummy_key";
    private static final String EXPECTED_QUERY_BY_TITLE_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=40&startIndex=0" +
            "&q=intitle:Krzyżacy&key=dummy_key&fields=kind,totalItems,items(volumeInfo/title,volumeInfo/subtitle,volum" +
            "eInfo/authors,volumeInfo/industryIdentifiers,volumeInfo/imageLinks)";
    private static final String EXPECTED_QUERY_BY_ISBN_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:9785040223008&key=dummy_key";
    private static final String EXPECTED_THUMBNAIL_URL
            = "http://books.google.com/books/content?id=jA7vDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api";

    @Mock
    private RestTemplate restTemplate;

    private LibraryService libraryService;

    @Before
    public void setUp() {
        this.libraryService = new LibraryServiceGoogleBooksImpl(KEY, this.restTemplate);
    }

    @Test
    public void searchForBooksWithIsbnByTitle() throws Exception {
        String jsonResponse = StreamUtils.copyToString(new ClassPathResource("tests/google_query_by_title.json").getInputStream(), Charset.defaultCharset());

        ObjectMapper objectMapper = new ObjectMapper();
        Response response = objectMapper.readValue(jsonResponse, Response.class);

        Mockito.when(this.restTemplate.getForEntity(any(String.class), any())).thenAnswer(
                invocation -> {
                    String url = invocation.getArgument(0);
                    assertEquals(EXPECTED_QUERY_BY_TITLE_URL, url);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                });

        List<Book> bookList = this.libraryService.searchForBooksWithIsbnByTitle("Krzyżacy");
        assertEquals(10, bookList.size());
        Book book = bookList.get(0);
        assertEquals("Krzyżacy", book.getTitle());
        assertEquals(null, book.getSubtitle());
        assertEquals(null, book.getEbook());
        assertEquals(EXPECTED_THUMBNAIL_URL, book.getThumbnailUrl());
        assertEquals(1, book.getAuthors().size());
        assertEquals("Henryk Sienkiewicz", book.getAuthors().get(0));
        assertEquals("5040223005", book.getIsbn().getIsbn10());
        assertEquals("9785040223008", book.getIsbn().getIsbn13());
    }

    @Test
    public void searchForBookByIsbn() throws Exception {
        String jsonResponse = StreamUtils.copyToString(new ClassPathResource("tests/google_query_by_isbn.json").getInputStream(), Charset.defaultCharset());

        ObjectMapper objectMapper = new ObjectMapper();
        Response response  = objectMapper.readValue(jsonResponse, Response.class);

        Mockito.when(this.restTemplate.getForEntity(any(String.class), any())).thenAnswer(
                invocation -> {
                    String url = invocation.getArgument(0);
                    assertEquals(EXPECTED_QUERY_BY_ISBN_URL, url);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                });

        Book book = this.libraryService.searchForBookByIsbn(new Isbn(null, "9785040223008"));

        assertEquals("Krzyżacy", book.getTitle());
        assertEquals(null, book.getSubtitle());
        assertEquals(true, book.getEbook());
        assertEquals(EXPECTED_THUMBNAIL_URL, book.getThumbnailUrl());
        assertEquals(1, book.getAuthors().size());
        assertEquals("Henryk Sienkiewicz", book.getAuthors().get(0));
        assertEquals("5040223005", book.getIsbn().getIsbn10());
        assertEquals("9785040223008", book.getIsbn().getIsbn13());
    }

}
