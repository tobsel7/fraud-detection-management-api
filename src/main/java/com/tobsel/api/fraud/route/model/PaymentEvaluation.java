package com.tobsel.api.fraud.route.model;

import java.util.UUID;

public record PaymentEvaluation(
    UUID id,
    UUID userId,
    String amount,
    String timestamp
) {}