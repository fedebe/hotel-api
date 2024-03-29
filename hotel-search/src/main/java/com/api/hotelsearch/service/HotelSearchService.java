package com.api.hotelsearch.service;

import com.api.hotelsearch.exception.HotelSearchDoesNotExistException;
import com.api.hotelsearch.model.HotelSearch;
import com.api.hotelsearch.model.HotelSearchCount;
import com.api.hotelsearch.model.HotelSearchId;

public interface HotelSearchService {
    
    public HotelSearchId search(final HotelSearch hotelSearch);
    
    public HotelSearchCount count(final HotelSearchId hotelSearchId) throws HotelSearchDoesNotExistException;
    
}
