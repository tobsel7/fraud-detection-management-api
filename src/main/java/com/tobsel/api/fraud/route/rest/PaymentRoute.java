package com.tobsel.api.fraud.route.rest;

import com.tobsel.api.fraud.processor.PaymentEventProcessor;
import com.tobsel.api.fraud.processor.PaymentEvaluationProcessor;
import com.tobsel.api.fraud.route.model.PaymentEvaluation;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentRoute extends RouteBuilder {

    private final PaymentEventProcessor paymentEventProcessor;
    private final PaymentEvaluationProcessor paymentEvaluationProcessor;

    public PaymentRoute(
            PaymentEventProcessor paymentEventProcessor,
            PaymentEvaluationProcessor paymentEvaluationProcessor
    ) {
        this.paymentEventProcessor = paymentEventProcessor;
        this.paymentEvaluationProcessor = paymentEvaluationProcessor;
    }

    @Override
    public void configure() {
        rest("/payment-evaluations")
                .post()
                .type(PaymentEvaluation.class)
                .to("direct:processPaymentEvaluation");

        from("direct:processPaymentEvaluation")
                .process(paymentEventProcessor)
                .process(paymentEvaluationProcessor)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));;
    }
}
