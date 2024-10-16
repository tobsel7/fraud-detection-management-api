package com.tobsel.api.fraud.route.model;

import java.util.UUID;

public record PaymentEvaluationResult(
    UUID id,
    PaymentStatus status
) {}