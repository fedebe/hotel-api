package com.api.hotelsearch.service.impl;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.api.hotelsearch.exception.MessagesProducerServiceException;
import com.api.hotelsearch.model.HotelSearch;
import com.api.hotelsearch.service.MessagesProducerService;

@Service
public class MessagesProducerServiceImpl implements MessagesProducerService {
    
    private KafkaTemplate<String, HotelSearch> kafkaTemplate;
    
    private String topicName;
    
    @Autowired
    public MessagesProducerServiceImpl(KafkaTemplate<String, HotelSearch> kafkaTemplate, @Value("${kafka.topic.name.producer}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void send(UUID key, HotelSearch hotelSearch) throws MessagesProducerServiceException {
        try {
            kafkaTemplate.send(topicName, key.toString(), hotelSearch).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new MessagesProducerServiceException(e);
        }
    }

}
