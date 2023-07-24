package com.bs.payment.repositories;

import com.bs.payment.models.Payment;

import java.util.Optional;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByIdAndIsDeletedFalse(Long id);

    boolean existsByReservationIdAndUserIdAndIsDeletedFalse(Long reservationId, String userId);

}
