package com.bs.payment.exceptions.internalServer;

public class SchedulerNotFoundException extends ExternalServiceException {
    private static final String SCHEDULE_NOT_FOUND_MESSAGE_FORMAT = "Schedule not found for reservationId: %s and userId: %s";

    public SchedulerNotFoundException(String reservationId, String userId) {
        super(String.format(SCHEDULE_NOT_FOUND_MESSAGE_FORMAT, reservationId, userId));
    }
}
