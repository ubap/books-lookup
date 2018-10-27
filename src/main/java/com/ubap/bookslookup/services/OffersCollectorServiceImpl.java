package com.ubap.bookslookup.services;

import com.ubap.bookslookup.model.Isbn;
import com.ubap.bookslookup.model.Offer;
import com.ubap.bookslookup.model.StoreOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffersCollectorServiceImpl implements OffersCollectorService {

    private List<ShopService> bookShopServiceList;
    private CurrencyService currencyService;

    @Autowired
    public OffersCollectorServiceImpl(List<ShopService> bookShopServiceList, CurrencyService currencyService) {
        this.bookShopServiceList = bookShopServiceList;
        this.currencyService = currencyService;
    }

    @Override
    public List<StoreOffer> getOffersSortedFromCheapest(Isbn isbn, String currency) {
        List<StoreOffer> storeOfferList = new ArrayList<>();

        for (ShopService shopService : this.bookShopServiceList) {
            Offer offer = shopService.getCheapestBookOfferByIsbn(isbn);
            if (offer != null) {
                storeOfferList.add(new StoreOffer(
                        shopService.getStoreLogoUrl(), shopService.getStoreName(), offer));
            }
        }

        storeOfferList = storeOfferList.stream().map(storeOffer -> {
            BigDecimal priceDefaultCurrency = OffersCollectorServiceImpl.this.currencyService.convertCurrency(
                    storeOffer.getCurrency(), currency, storeOffer.getPrice(),
                    OffersCollectorServiceImpl.this.currencyService.getRates());

            return new StoreOffer(storeOffer.getStoreLogoUrl(), storeOffer.getStoreName(),
                    new Offer(storeOffer.getUrl(), currency, priceDefaultCurrency));
        }).collect(Collectors.toList());

        storeOfferList.sort(Comparator.comparing(Offer::getPrice));

        return storeOfferList;
    }

}
