package com.bs.payment.services;

import com.bs.payment.dtos.request.PaymentCreateDto;
import com.bs.payment.dtos.response.PaymentGetResponseDto;

public interface PaymentService {

    void createPayment(PaymentCreateDto paymentCreateDto);
    void deletePayment(final Long id);
    PaymentGetResponseDto getPaymentById(Long id);

}
