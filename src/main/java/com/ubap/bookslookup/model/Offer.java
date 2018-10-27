package com.ubap.bookslookup.model;

import lombok.Getter;

import java.math.BigDecimal;

public class Offer {

    @Getter
    private String url;

    @Getter
    private String currency;

    @Getter
    private BigDecimal price;

    public Offer(String url, String currency, BigDecimal price) {
        this.url = url;
        this.currency = currency;
        this.price = price;
    }
}
