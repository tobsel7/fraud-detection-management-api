package com.tobsel.api.fraud.route.model;

import java.util.UUID;

public record PaymentEvaluationStatus(
    UUID id,
    PaymentStatus status
) {}