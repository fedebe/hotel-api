package com.api.hotelavailability.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.hotelavailability.domain.HotelSearchDAO;
import com.api.hotelavailability.exception.HotelAvailabilityException;
import com.api.hotelavailability.model.HotelSearch;
import com.api.hotelavailability.repository.HotelAvailabilityRepository;
import com.api.hotelavailability.service.HotelAvailabilityService;

@Service
public class HotelAvailabilityServiceImpl implements HotelAvailabilityService {

    private HotelAvailabilityRepository hotelAvailabilityRepository;
    
    @Autowired
    public HotelAvailabilityServiceImpl(HotelAvailabilityRepository hotelAvailabilityRepository) {
        this.hotelAvailabilityRepository = hotelAvailabilityRepository;
    }

    @Override
    public void search(HotelSearch hotelSearch, String key) throws HotelAvailabilityException {
        
        try {
            HotelSearchDAO hotel = new HotelSearchDAO(key,
                    hotelSearch.hotelId(),
                    hotelSearch.checkIn(),
                    hotelSearch.checkOut(),
                    hotelSearch.ages());
            
            hotelAvailabilityRepository.save(hotel);
        } catch(Exception e) {
            throw new HotelAvailabilityException(e);
        }
    }

}
