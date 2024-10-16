package com.tobsel.api.fraud.route;

import com.tobsel.api.fraud.config.KafkaTopic;
import com.tobsel.api.fraud.config.RestEndpoint;
import com.tobsel.api.fraud.processor.PaymentEventProcessor;
import com.tobsel.api.fraud.processor.PaymentEvaluationProcessor;
import com.tobsel.api.fraud.route.model.PaymentConfirmationEvent;
import com.tobsel.api.fraud.route.model.PaymentEvaluation;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentRoute extends RouteBuilder {

    private final PaymentEvaluationProcessor paymentEvaluationProcessor;
    private final PaymentEventProcessor paymentEventProcessor;

    public PaymentRoute(
        PaymentEvaluationProcessor paymentEvaluationProcessor,
        PaymentEventProcessor paymentEventProcessor
    ) {
        this.paymentEvaluationProcessor = paymentEvaluationProcessor;
        this.paymentEventProcessor = paymentEventProcessor;
    }

    @Override
    public void configure() {
        rest(RestEndpoint.PAYMENT_EVALUATION)
            .post()
            .type(PaymentEvaluation.class)
            .to("direct:processPaymentEvaluation");

        from("direct:processPaymentEvaluation")
            .process(paymentEvaluationProcessor)
            .process(paymentEventProcessor);

        from("kafka:" + KafkaTopic.PAYMENT_CONFIRMATION)
            .unmarshal().json(PaymentConfirmationEvent.class)
            .process(paymentEventProcessor);
    }
}
