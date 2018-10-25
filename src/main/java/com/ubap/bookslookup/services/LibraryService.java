package com.ubap.bookslookup.services;

import com.ubap.bookslookup.model.Book;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface LibraryService {

    @NonNull
    List<Book> searchForBooksWithIsbnByTitle(@NonNull String title);

    @Nullable
    Book searchForBookByIsbn(@NonNull String isbn);
}
