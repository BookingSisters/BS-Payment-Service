package com.bs.payment.models;

import com.bs.payment.enums.PaymentStatus;
import com.bs.payment.enums.PaymentType;
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
    private Long price;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(nullable=false)
    private String userId;

    private LocalDateTime payedAt;

    @Builder
    public Payment(@NonNull final Long reservationId, @NonNull final Long price, @NonNull final PaymentStatus status, @NonNull final PaymentType type, @NonNull final String userId) {
        this.reservationId = reservationId;
        this.price = price;
        this.status = status;
        this.type = type;
        this.userId = userId;
    }

    public void deletePayment() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
