package com.api.hotelsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorInfo(@JsonProperty("messages") String[] messages, 
                        @JsonProperty("status_code") int statusCode, 
                        @JsonProperty("uri") String uriRequested) {

}
