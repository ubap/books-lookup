package com.ubap.bookslookup.providers.ebay.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindItemsByKeywordsResponse {

    @Getter
    @JsonProperty("ack")
    private List<String> ack = null;

    @Getter
    @JsonProperty("version")
    private List<String> version = null;

    @Getter
    @JsonProperty("timestamp")
    private List<String> timestamp = null;

    @Getter
    @JsonProperty("searchResult")
    private List<SearchResult> searchResult = null;

    @Getter
    @JsonProperty("itemSearchURL")
    private List<String> itemSearchURL = null;

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
