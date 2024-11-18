package com.tobsel.api.fraud.route;

import com.tobsel.api.fraud.config.KafkaTopic;
import com.tobsel.api.fraud.config.RestEndpoint;
import com.tobsel.api.fraud.processor.PaymentEventProcessor;
import com.tobsel.api.fraud.processor.PaymentEvaluationProcessor;
import com.tobsel.api.fraud.route.model.PaymentConfirmationEvent;
import com.tobsel.api.fraud.route.model.PaymentEvaluation;
import com.tobsel.api.fraud.route.model.PaymentEvaluationResult;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
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
            .convertBodyTo(PaymentEvaluationResult.class)
            .wireTap("direct:producePaymentEvaluationResult")
            .process(paymentEventProcessor);

        from("direct:producePaymentEvaluationResult")
            .setHeader(KafkaConstants.KEY, simple("${body.id}"))
            .marshal().json()
            .to("kafka:" + KafkaTopic.PAYMENT_EVALUATION_RESULT);

        from("kafka:" + KafkaTopic.PAYMENT_CONFIRMATION)
            .unmarshal().json(PaymentConfirmationEvent.class)
            .process(paymentEventProcessor);
    }
}
