package com.ubap.bookslookup.providers.googlebooks.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleInfo {

    @JsonProperty("country")
    private String country;

    @Getter
    @JsonProperty("saleability")
    private String saleability;

    @Getter
    @JsonProperty("isEbook")
    private Boolean isEbook;

    @Getter
    @JsonProperty("buyLink")
    private String buyLink;

    @JsonProperty("listPrice")
    private Price listPrice;

    @Getter
    @JsonProperty("retailPrice")
    private Price retailPrice;

    @JsonProperty("offers")
    private List<Offer> offers = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}