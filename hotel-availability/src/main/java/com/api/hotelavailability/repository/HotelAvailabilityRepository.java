package com.api.hotelavailability.repository;

import java.util.List;

import com.api.hotelavailability.domain.HotelSearchDAO;

public interface HotelAvailabilityRepository {
    
    void saveAll(List<HotelSearchDAO> hotelSearches);
}
