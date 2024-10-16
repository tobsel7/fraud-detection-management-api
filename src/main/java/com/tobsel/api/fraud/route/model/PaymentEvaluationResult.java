package com.tobsel.api.fraud.route.model;

import java.util.UUID;

public record PaymentEvaluationResult(
    PaymentEvaluation evaluation,
    Status result
) implements PaymentEvent {

    public enum Status {
        ACCEPTED,
        REJECTED
    }

    @Override
    public UUID id() { return this.evaluation.id(); }

    @Override
    public UUID userId() { return this.evaluation.userId(); }

    @Override
    public String amount() { return this.evaluation.amount(); }

    @Override
    public String timestamp() { return this.evaluation.timestamp(); }

    @Override
    public PaymentStatus status() {
        return this.result == Status.ACCEPTED ? PaymentStatus.PENDING : PaymentStatus.REJECTED;
    }
}