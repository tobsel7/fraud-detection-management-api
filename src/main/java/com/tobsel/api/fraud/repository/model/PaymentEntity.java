package com.tobsel.api.fraud.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "payment")
public class PaymentEntity {
    @Id private UUID id;
    private UUID userId;
    private String amount;
    private String timestamp;
    private String paymentStatus;

    public PaymentEntity() {}

    public PaymentEntity(
        UUID id,
        UUID userId,
        String amount,
        String timestamp,
        String paymentStatus
    ) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.paymentStatus = paymentStatus;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}