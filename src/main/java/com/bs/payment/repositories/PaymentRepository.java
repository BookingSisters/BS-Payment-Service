package com.bs.payment.repositories;

import com.bs.payment.models.Payment;

import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByIdAndIsDeletedFalse(Long id);

    boolean existsByReservationIdAndUserIdAndIsDeletedFalse(Long reservationId, String userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Payment> findWithLockingByIdAndIsDeletedFalse(@Param("id") Long id);
}
