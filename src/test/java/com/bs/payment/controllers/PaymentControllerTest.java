package com.bs.payment.controllers;

import com.bs.payment.dtos.request.PaymentCreateDto;
import com.bs.payment.dtos.response.PaymentGetResponseDto;
import com.bs.payment.enums.PaymentStatus;
import com.bs.payment.enums.PaymentType;
import com.bs.payment.exceptions.badReqeust.ResourceNotFoundException;
import com.bs.payment.models.Payment;
import com.bs.payment.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Long paymentId;
    Payment payment;

    @BeforeEach
    public void setup() {
        paymentId = 1002L;
        payment = getPayment();
    }

    @Test
    @DisplayName("유효한 데이터로 결제 생성 API 호출 시 성공")
    void testCreatePayment() throws Exception {

        PaymentCreateDto paymentCreateDto = PaymentCreateDto.builder()
                .reservationId(1L)
                .userId("user")
                .build();

        doNothing().when(paymentService).createPayment(any(PaymentCreateDto.class));

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentCreateDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("잘못된 데이터로 결제 생성 API 호출 시 실패")
    void testCreatePaymentFailure() throws Exception {

        PaymentCreateDto paymentCreateDto = PaymentCreateDto.builder().build();

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 결제 ID로 조회 API 호출 시 성공")
    void testGetPayment() throws Exception {

        PaymentGetResponseDto paymentGetResponseDto = PaymentGetResponseDto.builder()
                .reservationId(1L)
                .price(10000L)
                .userId("user")
                .status(PaymentStatus.PENDING)
                .type(PaymentType.KAKAO_PAY)
                .build();

        doReturn(paymentGetResponseDto).when(paymentService).getPaymentById(paymentId);

        mockMvc.perform(get("/payments/" + paymentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(paymentGetResponseDto.getPrice()));
    }

    @Test
    @DisplayName("존재하지 않는 결제 ID로 조회 API 호출 시 실패")
    void testGetPaymentFailure() throws Exception {

        doThrow(ResourceNotFoundException.class).when(paymentService).getPaymentById(paymentId);

        mockMvc.perform(get("/payments/" + paymentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 결제 ID로 결제 상태 업데이트 API 호출 시 성공")
    void testPaymentCompleteSuccess() throws Exception {

        doNothing().when(paymentService).complete(paymentId);

        mockMvc.perform(put("/payments/" + paymentId + "/complete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Payment status updated successfully"));
    }

    @Test
    @DisplayName("존재하지 않는 결제 ID로 결제 상태 업데이트 API 호출 시 실패")
    void testPaymentCompleteFailure() throws Exception {

        doThrow(ResourceNotFoundException.class).when(paymentService).complete(paymentId);

        mockMvc.perform(put("/payments/" + paymentId + "/complete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private static Payment getPayment() {
        return Payment.builder()
                .reservationId(1L)
                .price(10000L)
                .userId("user")
                .status(PaymentStatus.PENDING)
                .type(PaymentType.KAKAO_PAY)
                .build();
    }
}