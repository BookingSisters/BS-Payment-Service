package com.bs.payment.exceptions.internalServer;

public class InternalServiceException extends RuntimeException {

    public InternalServiceException(String message) {
        super(message);
    }
}
