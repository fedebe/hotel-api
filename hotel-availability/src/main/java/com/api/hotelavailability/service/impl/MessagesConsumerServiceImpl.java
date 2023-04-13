package com.api.hotelavailability.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
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

    @Override
    @KafkaListener(topics = "${kafka.topic.name.consumer}", properties = {"spring.json.value.default.type=com.api.hotelavailability.model.HotelSearch"})
    public void receive(@Payload HotelSearch hotelSearch,
                        @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        try {
            hotelAvailabilityService.search(hotelSearch, messageKey);
        } catch(HotelAvailabilityException e) {
            LOGGER.error("An error has occurred while trying to save hotel search", e);
        }
    }

}
