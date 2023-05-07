package com.api.hotelsearch.model;

import java.time.LocalDate;

import com.api.hotelsearch.validator.constraint.ValidDates;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;

import java.util.Collections;
import java.util.List;


@ValidDates(message = "Check-in date must be before or equal to check-out date.")
public record HotelSearch(
        @NotBlank(message = "Hotel Id field need to be completed.") 
        String hotelId,
        @NotNull(message = "Check-in field need to be completed.") 
        @FutureOrPresent(message = "Check-in must be a date present or future.") 
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,
        @NotNull(message = "Check-out field need to be completed.") 
        @FutureOrPresent(message = "Check-out must be a date present or future.") 
        @JsonFormat(pattern = "dd/MM/yyyy") 
        LocalDate checkOut,
        @NotEmpty(message = "Ages need to have at least one value.") 
        List<Integer> ages) {

    public HotelSearch(@NotBlank(message = "Hotel Id field need to be completed.") 
                       String hotelId,
                       @NotNull(message = "Check-in field need to be completed.") 
                       @FutureOrPresent(message = "Check-in must be a date present or future.") 
                       @JsonFormat(pattern = "dd/MM/yyyy") 
                       LocalDate checkIn,
                       @NotNull(message = "Check-out field need to be completed.") 
                       @FutureOrPresent(message = "Check-out must be a date present or future.") 
                       @JsonFormat(pattern = "dd/MM/yyyy") 
                       LocalDate checkOut,
                       @NotEmpty(message = "Ages need to have at least one value.") 
                       List<Integer> ages) {
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = Collections.unmodifiableList(ages);

    }
}
