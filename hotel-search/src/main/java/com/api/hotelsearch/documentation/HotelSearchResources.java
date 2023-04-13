package com.api.hotelsearch.documentation;

import com.api.hotelsearch.model.HotelSearch;
import com.api.hotelsearch.model.HotelSearchCount;
import com.api.hotelsearch.model.HotelSearchId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hotel Search", description = "These endpoints allow for queries about hotels")
public interface HotelSearchResources {

    @Operation(summary = "Allow to request a hotel search, returns an hotel search id.")
    public HotelSearchId search(@Parameter(name = "hotel search",
            description = "Hotel search json that cointains the data for do the search.") HotelSearch hotelSearch);
    
    @Operation(summary = "Returns the quantity of the hotel searches that are the same as the hotel search id passed by parameter.")
    public HotelSearchCount count(@Parameter(name = "hotelSearchId", in = ParameterIn.PATH, description = "Hotel Search Id") HotelSearchId hotelSearchId);
}
