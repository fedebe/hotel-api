package com.api.hotelsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HotelSearchId(@JsonProperty("searchId") String searchId) {

}
