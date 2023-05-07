package com.api.hotelavailability.service;

import java.util.List;

import org.springframework.messaging.Message;

import com.api.hotelavailability.model.HotelSearch;

public interface MessagesConsumerService {
    
    public void consume(List<Message<HotelSearch>> records);
}
