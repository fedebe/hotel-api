package com.api.hotelsearch.service;

import java.util.UUID;

import com.api.hotelsearch.model.HotelSearch;

public interface MessagesProducerService {
    
    public void send(final UUID key, final HotelSearch hotelSearch);

}
