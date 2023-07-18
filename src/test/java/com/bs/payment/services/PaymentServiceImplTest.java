package com.bs.payment.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.bs.payment.dtos.request.PaymentCreateDto;
import com.bs.payment.dtos.response.PaymentGetResponseDto;
import com.bs.payment.enums.PaymentStatus;
import com.bs.payment.enums.PaymentType;
import com.bs.payment.exceptions.ResourceNotFoundException;
import com.bs.payment.models.Payment;
import com.bs.payment.repositories.PaymentRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    private PaymentRepository paymentRepository;

    Long paymentId;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentId = 1002L;
        payment = getPayment();
    }

    @Test
    @DisplayName("유효한 결제 PaymentCreateDto가 주어졌을 때, 정상적으로 저장")
    void createPaymentTest() {
        PaymentCreateDto createDto = PaymentCreateDto.builder()
            .price(200000L)
            .type(PaymentType.KAKAO_PAY)
            .status(PaymentStatus.PENDING)
            .reservationId(111L)
            .userId("test")
            .build();

        paymentService.createPayment(createDto);

        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    @DisplayName("잘못된 PaymentCreateDto로 등록하는 경우, NullPointerException 예외 발생")
    void createPaymentWithInvalidIdTest() {

        PaymentCreateDto createDto = PaymentCreateDto.builder().build();

        assertThatThrownBy(() -> paymentService.createPayment(createDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("유효한 결제 ID가 주어졌을 때, 정상적으로 조회")
    void getPaymentByIdTest() {

        PaymentGetResponseDto paymentGetResponseDto = PaymentGetResponseDto.builder()
            .reservationId(1L)
            .price(10000L)
            .userId("user")
            .status(PaymentStatus.PENDING)
            .type(PaymentType.KAKAO_PAY)
            .build();

        doReturn(Optional.of(payment)).when(paymentRepository).findByIdAndIsDeletedFalse(paymentId);
        doReturn(paymentGetResponseDto).when(modelMapper).map(payment, PaymentGetResponseDto.class);

        PaymentGetResponseDto result = paymentService.getPaymentById(paymentId);

        assertEquals(1L, result.getReservationId());
        assertEquals(10000L, result.getPrice());
        assertEquals("user", result.getUserId());
        assertEquals(PaymentStatus.PENDING, result.getStatus());
        assertEquals(PaymentType.KAKAO_PAY, result.getType());
    }

    @Test
    @DisplayName("존재하지 않는 결제 ID가 주어졌을 때, ResourceNotFoundException 예외 발생")
    void getPaymentByInvalidIdTest() {
        doThrow(ResourceNotFoundException.class).when(paymentRepository)
            .findByIdAndIsDeletedFalse(paymentId);

        assertThrows(ResourceNotFoundException.class,
            () -> paymentService.getPaymentById(paymentId));
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