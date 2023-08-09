package com.bs.payment.exceptions.internalServer;
public class InvalidPaymentStatusException extends InternalServiceException {
    private static final String INVALID_STATUS_MESSAGE_FORMAT = "Invalid payment status change. Current status: %s, Requested change: %s";

    public InvalidPaymentStatusException(String currentStatus, String requestedChange) {
        super(String.format(INVALID_STATUS_MESSAGE_FORMAT, currentStatus, requestedChange));
    }
}