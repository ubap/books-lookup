package com.ubap.bookslookup.providers.googlebooks.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Item {

    @Getter
    @JsonProperty("kind")
    private String kind;

    @Getter
    @JsonProperty("id")
    private String id;

    @Getter
    @JsonProperty("etag")
    private String etag;

    @Getter
    @JsonProperty("selfLink")
    private String selfLink;

    @Getter
    @JsonProperty("volumeInfo")
    private VolumeInfo volumeInfo;

    @Getter
    @JsonProperty("saleInfo")
    private SaleInfo saleInfo;

    @Getter
    @JsonProperty("accessInfo")
    private AccessInfo accessInfo;

    @Getter
    @JsonProperty("searchInfo")
    private SearchInfo searchInfo;

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