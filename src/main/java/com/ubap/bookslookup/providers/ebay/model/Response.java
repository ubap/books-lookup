package com.ubap.bookslookup.providers.ebay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class Response {

    @Getter
    @JsonProperty("findItemsByKeywordsResponse")
    private List<FindItemsByKeywordsResponse> findItemsByKeywordsResponse;
}
