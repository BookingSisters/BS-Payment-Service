package com.bs.payment.dtos.request;

import com.bs.payment.enums.PaymentStatus;
import com.bs.payment.enums.PaymentType;
import com.bs.payment.models.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PaymentCreateDto {

    @NotNull(message = "ReservationId must not be null")
    private Long reservationId;

    @NotNull(message = "UserId must not be null")
    private String userId;

    public Payment toEntity(){
        return Payment.builder()
                .reservationId(this.reservationId)
                .userId(this.userId)
                .status(PaymentStatus.PENDING)
                .build();
    }
}
