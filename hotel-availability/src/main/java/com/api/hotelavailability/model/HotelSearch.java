package com.api.hotelavailability.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


public record HotelSearch(
        String hotelId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") 
        @JsonDeserialize(using = LocalDateDeserializer.class) 
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate checkIn,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") 
        @JsonDeserialize(using = LocalDateDeserializer.class) 
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate checkOut, 
        int[] ages) {
}
