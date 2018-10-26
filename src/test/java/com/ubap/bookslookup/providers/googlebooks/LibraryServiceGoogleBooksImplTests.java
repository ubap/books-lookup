package com.ubap.bookslookup.providers.googlebooks;

import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.model.Isbn;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryServiceGoogleBooksImplTests {

    @Autowired
    private LibraryServiceGoogleBooksImpl googleBooksLibraryService;

    @Test
    public void testDI() {
        Assertions.assertNotNull(this.googleBooksLibraryService);
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
