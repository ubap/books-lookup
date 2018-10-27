package com.ubap.bookslookup.providers.fixer.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Response {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("timestamp")
    private Integer timestamp;

    @JsonProperty("base")
    private String base;

    @JsonProperty("date")
    private String date;

    @Getter
    @JsonProperty("rates")
    private Map<String, BigDecimal> rates;

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
