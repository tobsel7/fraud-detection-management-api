package com.tobsel.api.fraud.route.model;

public record RawPaymentEvaluationResult(
        PaymentEvaluation paymentEvaluation,
        PaymentEvaluationResult.Status result
) {}
