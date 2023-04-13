package com.api.hotelsearch.service;

import java.util.UUID;

import com.api.hotelsearch.exception.MessagesProducerServiceException;
import com.api.hotelsearch.model.HotelSearch;

public interface MessagesProducerService {
    
    public void send(UUID key, HotelSearch hotelSearch) throws MessagesProducerServiceException;

}
