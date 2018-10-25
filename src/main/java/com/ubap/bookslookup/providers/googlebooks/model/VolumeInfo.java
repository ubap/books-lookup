package com.ubap.bookslookup.providers.googlebooks.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolumeInfo {

    @Getter
    @JsonProperty("title")
    private String title;

    @Getter
    @JsonProperty("authors")
    private List<String> authors = null;

    @JsonProperty("publishedDate")
    private String publishedDate;

    @Getter
    @JsonProperty("industryIdentifiers")
    private List<IndustryIdentifier> industryIdentifiers = null;

    @JsonProperty("pageCount")
    private Integer pageCount;

    @JsonProperty("printType")
    private String printType;

    @JsonProperty("categories")
    private List<String> categories = null;

    @JsonProperty("averageRating")
    private Integer averageRating;

    @JsonProperty("ratingsCount")
    private Integer ratingsCount;

    @JsonProperty("maturityRating")
    private String maturityRating;

    @JsonProperty("allowAnonLogging")
    private Boolean allowAnonLogging;

    @JsonProperty("contentVersion")
    private String contentVersion;

    @Getter
    @JsonProperty("imageLinks")
    private ImageLinks imageLinks;

    @JsonProperty("language")
    private String language;

    @JsonProperty("previewLink")
    private String previewLink;

    @JsonProperty("infoLink")
    private String infoLink;

    @JsonProperty("canonicalVolumeLink")
    private String canonicalVolumeLink;

    @Getter
    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("description")
    private String description;

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