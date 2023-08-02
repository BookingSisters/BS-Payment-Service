package com.bs.payment.models;

import com.bs.payment.enums.PaymentStatus;
import com.bs.payment.enums.PaymentType;
import com.bs.payment.exceptions.internalServer.InvalidPaymentStatusException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long reservationId;

    @Column(nullable=false)
    private String userId;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private Long price;

    private LocalDateTime payedAt;

    @Builder
    public Payment(@NonNull final Long reservationId, @NonNull final String userId, final PaymentStatus status, final PaymentType type, final Long price) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.status = status;
        this.type = type;
        this.price = price;
    }

    public void deletePayment() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void makeComplete() {
        if (this.status != PaymentStatus.PENDING) {
            throw new InvalidPaymentStatusException(this.status.toString(), PaymentStatus.COMPLETE.toString());
        }
        this.payedAt = LocalDateTime.now();
        this.status = PaymentStatus.COMPLETE;
    }

}
