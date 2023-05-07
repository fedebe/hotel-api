package com.api.hotelavailability.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record HotelSearch(
        String hotelId,
        @JsonFormat(pattern = "dd/MM/yyyy") 
        LocalDate checkIn,
        @JsonFormat(pattern = "dd/MM/yyyy") 
        LocalDate checkOut, 
        List<Integer> ages) {
}
