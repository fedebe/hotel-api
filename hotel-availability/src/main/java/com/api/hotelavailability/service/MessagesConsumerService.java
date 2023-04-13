package com.api.hotelavailability.service;

import com.api.hotelavailability.model.HotelSearch;

public interface MessagesConsumerService {
    
    public void receive(HotelSearch hotelSearch, String messageKey);
}
