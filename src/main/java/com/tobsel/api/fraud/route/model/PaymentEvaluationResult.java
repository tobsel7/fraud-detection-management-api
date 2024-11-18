package com.tobsel.api.fraud.route.model;

import java.util.UUID;

public record PaymentEvaluationResult(
    UUID id,
    UUID userId,
    String amount,
    String timestamp,
    PaymentStatus status
) implements PaymentEvent {

    public enum Status {
        ACCEPTED,
        REJECTED
    }
}

