package com.ubap.bookslookup.providers.googlebooks.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class AccessInfo {

    @JsonProperty("country")
    private String country;

    @JsonProperty("viewability")
    private String viewability;

    @JsonProperty("embeddable")
    private Boolean embeddable;

    @JsonProperty("publicDomain")
    private Boolean publicDomain;

    @JsonProperty("textToSpeechPermission")
    private String textToSpeechPermission;

    @JsonProperty("epub")
    private Format epub;

    @JsonProperty("pdf")
    private Format pdf;

    @JsonProperty("webReaderLink")
    private String webReaderLink;

    @JsonProperty("accessViewStatus")
    private String accessViewStatus;

    @JsonProperty("quoteSharingAllowed")
    private Boolean quoteSharingAllowed;

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