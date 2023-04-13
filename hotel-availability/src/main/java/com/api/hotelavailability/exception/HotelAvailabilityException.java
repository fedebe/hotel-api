package com.api.hotelavailability.exception;

public class HotelAvailabilityException extends RuntimeException {

    private static final long serialVersionUID = 4512586269690348162L;

    public HotelAvailabilityException(Exception e) {
        super(e);
    }
}
