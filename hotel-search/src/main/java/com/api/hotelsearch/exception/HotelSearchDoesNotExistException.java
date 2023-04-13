package com.api.hotelsearch.exception;

public class HotelSearchDoesNotExistException extends RuntimeException {
    
    private static final long serialVersionUID = 4646052424317428951L;

    public HotelSearchDoesNotExistException(String searchId) {
        super("Hotel search " + searchId + " does not exist.");
    }
}
