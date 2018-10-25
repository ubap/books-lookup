package com.ubap.bookslookup.providers.googlebooks;

import com.ubap.bookslookup.model.Book;
import com.ubap.bookslookup.services.LibraryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleBooksLibraryServiceImpl implements LibraryService {

    @Value("${keys.googleApi}")
    private String key;

    @Override
    public List<Book> searchForBooksWithIsbnByTitle(String title) {
        return null;
    }

    @Override
    public Book searchForBookByIsbn(String isbn) {
        return null;
    }
}
