package com.api.hotelavailability.service;

import com.api.hotelavailability.exception.HotelAvailabilityException;
import com.api.hotelavailability.model.HotelSearch;

public interface HotelAvailabilityService {
    
    public void search(HotelSearch hotelSearch, String key) throws HotelAvailabilityException;

}
