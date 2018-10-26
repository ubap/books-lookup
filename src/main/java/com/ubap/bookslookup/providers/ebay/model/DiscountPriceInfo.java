package com.ubap.bookslookup.providers.ebay.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountPriceInfo {

    @JsonProperty("originalRetailPrice")
    private List<Price> originalRetailPrice = null;

    @JsonProperty("pricingTreatment")
    private List<String> pricingTreatment = null;

    @JsonProperty("soldOnEbay")
    private List<String> soldOnEbay = null;

    @JsonProperty("soldOffEbay")
    private List<String> soldOffEbay = null;
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
