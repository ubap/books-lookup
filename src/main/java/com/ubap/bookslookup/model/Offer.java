package com.ubap.bookslookup.model;

import lombok.Getter;

public class Offer {

    @Getter
    private String url;

    @Getter
    private String currency;

    @Getter
    private int price;

    public Offer(String url, String currency, int price) {
        this.url = url;
        this.currency = currency;
        this.price = price;
    }
}
