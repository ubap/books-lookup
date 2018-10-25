package com.ubap.bookslookup.model;

import lombok.Getter;
import org.springframework.lang.Nullable;

public class Isbn {
    @Getter
    @Nullable
    private String isbn10;

    @Getter
    @Nullable
    private String isbn13;

    public Isbn(@Nullable String isbn10, @Nullable String isbn13) {
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }
}
