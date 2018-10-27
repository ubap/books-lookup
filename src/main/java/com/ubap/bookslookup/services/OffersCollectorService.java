package com.ubap.bookslookup.services;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.StoreOffer;
import org.springframework.lang.NonNull;

import java.util.List;

public interface OffersCollectorService {

    @NonNull
    List<StoreOffer> getOffersSortedFromCheapest(@NonNull Isbn isbn, @NonNull String currency);

}
