package com.bs.payment.exceptions.badReqeust;

public class PaymentAlreadyExistsException extends InvalidValueException {

    private static final String ALREADY_EXISTS_MESSAGE_FORMAT = "Payment already exists for reservationId: %s, userId: %s";

    public PaymentAlreadyExistsException(Long reservationId, String userId) {
        super(String.format(ALREADY_EXISTS_MESSAGE_FORMAT, reservationId, userId));
    }
}