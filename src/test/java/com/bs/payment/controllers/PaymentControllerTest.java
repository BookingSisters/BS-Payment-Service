package com.bs.payment.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bs.payment.dtos.request.PaymentCreateDto;
import com.bs.payment.dtos.response.PaymentGetResponseDto;
import com.bs.payment.enums.PaymentStatus;
import com.bs.payment.enums.PaymentType;
import com.bs.payment.exceptions.ResourceNotFoundException;
import com.bs.payment.models.Payment;
import com.bs.payment.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
            .price(10000L)
            .userId("user")
            .status(PaymentStatus.PENDING)
            .type(PaymentType.KAKAO_PAY)
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

        doThrow(NullPointerException.class).when(paymentService)
            .createPayment(any(PaymentCreateDto.class));

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentCreateDto)))
            .andExpect(status().isInternalServerError());
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