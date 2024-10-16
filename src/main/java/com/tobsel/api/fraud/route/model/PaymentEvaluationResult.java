package com.tobsel.api.fraud.route.model;

import java.util.UUID;

public record PaymentEvaluationResult(
    UUID id,
    Status result
) {
    public enum Status {
        ACCEPTED,
        REJECTED
    }
}