package com.api.hotelsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HotelSearchCount(
        @JsonProperty("searchId") String searchId, 
        @JsonProperty("search") HotelSearch search, 
        @JsonProperty("count") Long count) {

}
