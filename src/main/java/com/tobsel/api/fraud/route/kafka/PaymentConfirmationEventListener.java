package com.tobsel.api.fraud.route.kafka;

import com.tobsel.api.fraud.config.Constants;
import com.tobsel.api.fraud.processor.PaymentEventProcessor;
import com.tobsel.api.fraud.route.model.PaymentConfirmationEvent;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentConfirmationEventListener extends RouteBuilder {

    private final PaymentEventProcessor paymentEventProcessor;

    public PaymentConfirmationEventListener(PaymentEventProcessor paymentEventProcessor) {
        this.paymentEventProcessor = paymentEventProcessor;
    }

    @Override
    public void configure() {
        from("kafka:" + Constants.KafkaTopic.PAYMENT)
                .unmarshal().json(PaymentConfirmationEvent.class)
                .process(paymentEventProcessor);
    }
}