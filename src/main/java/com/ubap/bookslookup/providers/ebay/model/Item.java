package com.ubap.bookslookup.providers.ebay.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {

    @JsonProperty("itemId")
    private List<String> itemId = null;

    @JsonProperty("title")
    private List<String> title = null;

    @JsonProperty("globalId")
    private List<String> globalId = null;

    @JsonProperty("galleryURL")
    private List<String> galleryURL = null;

    @Getter
    @JsonProperty("viewItemURL")
    private List<String> viewItemURL = null;

    @JsonProperty("paymentMethod")
    private List<String> paymentMethod = null;

    @JsonProperty("autoPay")
    private List<String> autoPay = null;

    @JsonProperty("location")
    private List<String> location = null;

    @JsonProperty("country")
    private List<String> country = null;

    @Getter
    @JsonProperty("sellingStatus")
    private List<SellingStatus> sellingStatus = null;

    @JsonProperty("returnsAccepted")
    private List<String> returnsAccepted = null;

    @JsonProperty("isMultiVariationListing")
    private List<String> isMultiVariationListing = null;

    @JsonProperty("topRatedListing")
    private List<String> topRatedListing = null;

    @JsonProperty("postalCode")
    private List<String> postalCode = null;

    @JsonProperty("galleryPlusPictureURL")
    private List<String> galleryPlusPictureURL = null;

    @JsonProperty("discountPriceInfo")
    private List<DiscountPriceInfo> discountPriceInfo = null;

    @JsonProperty("subtitle")
    private List<String> subtitle = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
