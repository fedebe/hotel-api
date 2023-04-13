package com.api.hotelsearch.model;

import java.time.LocalDate;

import com.api.hotelsearch.validator.constraint.ValidDates;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;

@ValidDates(message = "Check-in date must be before or equal to check-out date.")
public record HotelSearch(
        @NotBlank(message = "Hotel Id field need to be completed.")
        @JsonProperty("hotelId") String hotelId,
        @NotNull(message = "Check-in field need to be completed.")
        @FutureOrPresent(message = "Check-in must be a date present or future.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") 
        @JsonDeserialize(using = LocalDateDeserializer.class) 
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonProperty("checkIn") LocalDate checkIn,
        @NotNull(message = "Check-out field need to be completed.")
        @FutureOrPresent(message = "Check-out must be a date present or future.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") 
        @JsonDeserialize(using = LocalDateDeserializer.class) 
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonProperty("checkOut") LocalDate checkOut, 
        @NotEmpty(message = "Ages need to have at least one value.")
        @JsonProperty("ages") int[] ages) {

}
