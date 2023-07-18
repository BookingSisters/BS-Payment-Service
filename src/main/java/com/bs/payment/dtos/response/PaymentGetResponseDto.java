package com.bs.payment.dtos.response;

import com.bs.payment.enums.PaymentStatus;
import com.bs.payment.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PaymentGetResponseDto {

    private final Long reservationId;

    private final Long price;

    private final PaymentStatus status;

    private final PaymentType type;

    private final String userId;

}
