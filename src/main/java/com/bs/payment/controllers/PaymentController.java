package com.bs.payment.controllers;

import com.bs.payment.dtos.request.PaymentCreateDto;
import com.bs.payment.dtos.response.CommonResponseDto;
import com.bs.payment.dtos.response.PaymentGetResponseDto;
import com.bs.payment.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseDto createPayment(@RequestBody @Valid final PaymentCreateDto paymentCreateDto) {
        paymentService.createPayment(paymentCreateDto);
        return new CommonResponseDto("200", "successfully created");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto deletePayment(
        @PathVariable final Long id) {
        paymentService.deletePayment(id);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully deleted");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentGetResponseDto getPayment(@PathVariable final Long id) {
        return paymentService.getPaymentById(id);
    }
}
