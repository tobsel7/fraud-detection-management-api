package com.tobsel.api.fraud.route.model;

import java.util.UUID;

public record PaymentConfirmationEvent(
    UUID id,
    UUID userId,
    String amount,
    String timestamp,
    PaymentStatus status
) implements PaymentEvent {}
