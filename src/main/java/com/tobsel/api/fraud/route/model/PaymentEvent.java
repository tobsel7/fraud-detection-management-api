package com.tobsel.api.fraud.route.model;

import java.util.UUID;

public interface PaymentEvent {
    UUID id();
    UUID userId();
    String amount();
    String timestamp();
    PaymentStatus status();
}
