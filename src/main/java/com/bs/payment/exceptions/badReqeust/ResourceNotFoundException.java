package com.bs.payment.exceptions.badReqeust;

public class ResourceNotFoundException extends InvalidValueException {

    private static final String NOT_FOUND_MESSAGE_FORMAT = "Payment not found for %s ID: %s";

    public ResourceNotFoundException(String entity, String id) {
        super(String.format(NOT_FOUND_MESSAGE_FORMAT, entity, id));
    }
}