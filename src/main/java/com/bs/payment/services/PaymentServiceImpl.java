package com.bs.payment.services;

import com.bs.payment.dtos.request.PaymentCreateDto;
import com.bs.payment.dtos.response.PaymentGetResponseDto;
import com.bs.payment.exceptions.ResourceNotFoundException;
import com.bs.payment.models.Payment;
import com.bs.payment.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final ModelMapper modelMapper;

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void createPayment(final PaymentCreateDto paymentCreateDto) {

        log.info("Creating payment with PaymentCreateDto: {}", paymentCreateDto);

        Payment payment = paymentCreateDto.toEntity();

        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void deletePayment(final Long id) {

        log.info("deletePayment with ID: {}", id);

        Payment payment = getPayment(id);

        payment.deletePayment();
    }

    @Override
    public PaymentGetResponseDto getPaymentById(final Long id) {

        log.info("getPayment with ID: {}", id);

        Payment payment = getPayment(id);

        return modelMapper.map(payment, PaymentGetResponseDto.class);
    }

    private Payment getPayment(Long paymentId) {
        return paymentRepository.findByIdAndIsDeletedFalse(paymentId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment", String.valueOf(paymentId)));
    }
}
