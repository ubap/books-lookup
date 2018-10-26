package com.ubap.bookslookup.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class Book {
    @Getter
    private Isbn isbn;

    @Getter
    private String title;

    @Getter
    private String subtitle;

    @Getter
    private List<String> authors;

    @Getter
    private String thumbnailUrl;

    @Getter
    private Boolean ebook;

}
