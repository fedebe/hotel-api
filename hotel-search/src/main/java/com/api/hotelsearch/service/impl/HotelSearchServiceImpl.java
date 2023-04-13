package com.api.hotelsearch.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.hotelsearch.domain.HotelSearchDAO;
import com.api.hotelsearch.exception.HotelSearchDoesNotExistException;
import com.api.hotelsearch.model.HotelSearch;
import com.api.hotelsearch.model.HotelSearchCount;
import com.api.hotelsearch.model.HotelSearchId;
import com.api.hotelsearch.repositories.HotelSearchRepository;
import com.api.hotelsearch.service.HotelSearchService;
import com.api.hotelsearch.service.MessagesProducerService;

@Service
public class HotelSearchServiceImpl implements HotelSearchService {

    private HotelSearchRepository hotelSearchRepository;
    private MessagesProducerService queueService;

    @Autowired
    public HotelSearchServiceImpl(HotelSearchRepository hotelSearchRepository, MessagesProducerService queueService) {
        this.hotelSearchRepository = hotelSearchRepository;
        this.queueService = queueService;
    }

    @Override
    public HotelSearchId search(HotelSearch hotelSearch) {
        UUID key = UUID.randomUUID();
        
        queueService.send(key, hotelSearch);
        
        return new HotelSearchId(key.toString());
    }

    @Override
    public HotelSearchCount count(HotelSearchId hotelSearchId) throws HotelSearchDoesNotExistException {
        HotelSearchDAO hotelSearch = getHotelSearchDAO(hotelSearchId);

        Long count = hotelSearchRepository.countByHotelIdAndCheckinAndCheckoutAndAges(hotelSearch.getHotelId(),
                hotelSearch.getCheckin(), hotelSearch.getCheckout(), hotelSearch.getAges());

        HotelSearchCount hotelSearchCount = new HotelSearchCount(hotelSearchId.searchId(),
                                                                 new HotelSearch(hotelSearch.getHotelId(), 
                                                                                 hotelSearch.getCheckin(), 
                                                                                 hotelSearch.getCheckout(),
                                                                                 hotelSearch.getAges()), 
                                                                                 count);

        return hotelSearchCount;
    }

    private HotelSearchDAO getHotelSearchDAO(HotelSearchId hotelSearchId) {
        return hotelSearchRepository
                .findBySearchId(hotelSearchId.searchId())
                .orElseThrow(() -> new HotelSearchDoesNotExistException(hotelSearchId.searchId()));
    }

}
