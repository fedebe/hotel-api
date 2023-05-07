package com.api.hotelavailability.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void searches(Map<String, HotelSearch> hotelSearches) throws HotelAvailabilityException {
        try {
            List<HotelSearchDAO> searchesToSave = new ArrayList<>();

            for (Map.Entry<String, HotelSearch> hotelSearch : hotelSearches.entrySet()) {
                HotelSearchDAO hotel = new HotelSearchDAO(hotelSearch.getKey(),
                        hotelSearch.getValue().hotelId(),
                        hotelSearch.getValue().checkIn(),
                        hotelSearch.getValue().checkOut(),
                        hotelSearch.getValue().ages());
                searchesToSave.add(hotel);
            }

            hotelAvailabilityRepository.saveAll(searchesToSave);
        } catch (Exception e) {
            throw new HotelAvailabilityException(e);
        }
    }

}
