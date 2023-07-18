package com.bs.payment.enums;

public enum PaymentStatus {

    PENDING("pending"),
    COMPLETE("complete"),
    CANCEL("cancel"),
    TIME_OUT("timeOut");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public static PaymentStatus fromString(String statusValue) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.status.equalsIgnoreCase(statusValue)) return status;
        }
        throw new IllegalArgumentException("올바르지 않은 Status 값: " + statusValue);
    }
}
