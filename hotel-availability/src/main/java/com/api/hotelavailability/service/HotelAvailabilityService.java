package com.api.hotelavailability.service;

import java.util.Map;

import com.api.hotelavailability.exception.HotelAvailabilityException;
import com.api.hotelavailability.model.HotelSearch;

public interface HotelAvailabilityService {
    
    public void searches(Map<String, HotelSearch> hotelSearches) throws HotelAvailabilityException;

}
