package com.api.hotelsearch.exception;

public class MessagesProducerServiceException extends RuntimeException {
    
    private static final long serialVersionUID = -8969067415827513085L;

    public MessagesProducerServiceException(Exception exception) {
        super(exception.getMessage());
    }

}
