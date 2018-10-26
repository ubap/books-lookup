package com.ubap.bookslookup.model;

import lombok.Getter;

public class Breadcrumb {
    @Getter
    private String path;

    @Getter
    private String display;

    public Breadcrumb(String path, String display) {
        this.path = path;
        this.display = display;
    }
}
