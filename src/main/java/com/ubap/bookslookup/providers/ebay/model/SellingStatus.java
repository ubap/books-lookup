package com.ubap.bookslookup.providers.ebay.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellingStatus {

    @Getter
    @JsonProperty("currentPrice")
    private List<Price> currentPrice = null;

    @Getter
    @JsonProperty("convertedCurrentPrice")
    private List<Price> convertedCurrentPrice = null;

    @JsonProperty("sellingState")
    private List<String> sellingState = null;

    @JsonProperty("timeLeft")
    private List<String> timeLeft = null;

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
