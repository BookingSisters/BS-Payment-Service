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
@NoArgsConstructor
public class PaymentGetResponseDto {

    private Long reservationId;

    private Long price;

    private PaymentStatus status;

    private PaymentType type;

    private String userId;

}
