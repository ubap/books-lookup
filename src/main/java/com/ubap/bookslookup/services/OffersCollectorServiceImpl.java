package com.ubap.bookslookup.services;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import com.ubap.bookslookup.model.StoreOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OffersCollectorServiceImpl implements OffersCollectorService {

    private List<ShopService> bookShopServiceList;

    @Autowired
    public OffersCollectorServiceImpl(List<ShopService> bookShopServiceList) {
        this.bookShopServiceList = bookShopServiceList;
    }

    @Override
    public List<StoreOffer> getOffersSortedFromCheapest(Isbn isbn) {
        List<StoreOffer> storeOfferList = new ArrayList<>();

        for (ShopService shopService : this.bookShopServiceList) {
            Offer offer = shopService.getCheapestBookOfferByIsbn(isbn);
            if (offer != null) {
                storeOfferList.add(new StoreOffer(
                        shopService.getStoreLogoUrl(), shopService.getStoreName(), offer));
            }
        }

        return storeOfferList;
    }

}
