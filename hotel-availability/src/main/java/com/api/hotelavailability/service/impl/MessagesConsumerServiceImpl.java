package com.api.hotelavailability.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.api.hotelavailability.exception.HotelAvailabilityException;
import com.api.hotelavailability.model.HotelSearch;
import com.api.hotelavailability.service.HotelAvailabilityService;
import com.api.hotelavailability.service.MessagesConsumerService;

@Service
public class MessagesConsumerServiceImpl implements MessagesConsumerService {

    private HotelAvailabilityService hotelAvailabilityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagesConsumerServiceImpl.class);

    @Autowired
    public MessagesConsumerServiceImpl(HotelAvailabilityService hotelAvailabilityService) {
        this.hotelAvailabilityService = hotelAvailabilityService;
    }

    @KafkaListener(topics = "${kafka.topic.name.consumer}")
    public void consume(List<Message<HotelSearch>> records) {
        try {
            Map<String, HotelSearch> hotels = new HashMap<>();

            for (Message<HotelSearch> record : records) {
                record.getPayload();
                HotelSearch hotelSearch = record.getPayload();
                String key = record.getHeaders().get(KafkaHeaders.RECEIVED_KEY).toString();
                hotels.put(key, hotelSearch);
            }
            hotelAvailabilityService.searches(hotels);
        } catch (HotelAvailabilityException e) {
            LOGGER.error("An error has occurred while trying to save hotel search", e);
        }
    }

}
