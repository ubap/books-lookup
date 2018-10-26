package com.ubap.bookslookup.model;

import lombok.Getter;
import org.springframework.lang.NonNull;

public class StoreOffer extends Offer {

    @Getter
    private String storeLogoUrl;

    @Getter
    private String storeName;

    public StoreOffer(@NonNull String storeLogoUrl, @NonNull String storeName, @NonNull Offer offer) {
        super(offer.getUrl(), offer.getCurrency(), offer.getPrice());
        this.storeLogoUrl = storeLogoUrl;
        this.storeName = storeName;
    }
}
