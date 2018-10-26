package com.ubap.bookslookup.providers.googlebooks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.providers.googlebooks.model.Item;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleBooksLibraryServiceImplTests {

    @Autowired
    private GoogleBooksLibraryServiceImpl googleBooksLibraryService;

    @Test
    public void testDI() {
        Assertions.assertNotNull(this.googleBooksLibraryService);
    }

    @Test
    public void bookFromItem() throws Exception {
        String itemJson = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"title\": \"Krzyżacy\",\n" +
                "    \"authors\": [\n" +
                "     \"Henryk Sienkiewicz\"\n" +
                "    ],\n" +
                "    \"industryIdentifiers\": [\n" +
                "     {\n" +
                "      \"type\": \"ISBN_13\",\n" +
                "      \"identifier\": \"9785040223008\"\n" +
                "     },\n" +
                "     {\n" +
                "      \"type\": \"ISBN_10\",\n" +
                "      \"identifier\": \"5040223005\"\n" +
                "     }\n" +
                "    ],\n" +
                "    \"imageLinks\": {\n" +
                "     \"smallThumbnail\": \"http://books.google.com/books/content?id=jA7vDAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
                "     \"thumbnail\": \"http://books.google.com/books/content?id=jA7vDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
                "    }\n" +
                "   }\n" +
                "  },";

        ObjectMapper objectMapper = new ObjectMapper();
        Item item = objectMapper.readValue(itemJson, Item.class);

        Book book = GoogleBooksLibraryServiceImpl.bookFromItem(item);

        assertEquals("Krzyżacy", book.getTitle());
        assertNull(book.getSubtitle());
        assertEquals("5040223005", book.getIsbn().getIsbn10());
        assertEquals("9785040223008", book.getIsbn().getIsbn13());
        assertEquals(1, book.getAuthors().size());
        assertEquals("Henryk Sienkiewicz", book.getAuthors().get(0));
        assertEquals("http://books.google.com/books/content?id=jA7vDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api", book.getThumbnailUrl());
    }

    @Test
    public void searchForBooksWithIsbnByTitle() {
        List<Book> bookList = this.googleBooksLibraryService.searchForBooksWithIsbnByTitle("Krzyżacy");
        Assertions.assertNotEquals(0, bookList.size());
    }

    @Test
    public void searchForBookByIsbn() {
        Book book = this.googleBooksLibraryService.searchForBookByIsbn(new Isbn("5040223005", null));
        Assertions.assertEquals("Krzyżacy", book.getTitle());
    }

}
