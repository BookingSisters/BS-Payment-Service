package com.bs.payment.enums;

public enum PaymentType {
    KAKAO_PAY("카카오페이"),
    NAVER_PAY("네이버페이"),
    CREDIT_CARD("신용카드");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentType fromString(String type) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.name().equalsIgnoreCase(type) || paymentType.description.equals(type)) {
                return paymentType;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 Status 값: " + type);
    }
}
