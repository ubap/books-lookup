package com.ubap.bookslookup.services;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface ShopService {

    @NonNull
    String getStoreLogoUrl();

    @NonNull
    String getStoreName();

    @Nullable
    Offer getCheapestBookOfferByIsbn(@NonNull Isbn isbn);
}
