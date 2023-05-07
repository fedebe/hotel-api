package com.api.hotelsearch.validator;

import com.api.hotelsearch.model.HotelSearch;
import com.api.hotelsearch.validator.constraint.ValidDates;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HotelSearchValidDatesValidator implements ConstraintValidator<ValidDates, HotelSearch> {

    @Override
    public void initialize(ValidDates constraintAnnotation) {
    }

    @Override
    public boolean isValid(HotelSearch hotelSearch, ConstraintValidatorContext context) {
        
        return hotelSearch.checkIn() != null && hotelSearch.checkOut() != null && (
               hotelSearch.checkIn().isBefore(hotelSearch.checkOut()) || 
               hotelSearch.checkIn().isEqual(hotelSearch.checkOut()));
    }

}
