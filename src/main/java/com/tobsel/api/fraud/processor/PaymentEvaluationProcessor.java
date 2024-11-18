package com.tobsel.api.fraud.processor;

import com.tobsel.api.fraud.route.model.PaymentEvaluationResult;
import com.tobsel.api.fraud.route.model.PaymentEvaluation;
import com.tobsel.api.fraud.route.model.RawPaymentEvaluationResult;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentEvaluationProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEvaluationProcessor.class);

    @Override
    public void process(Exchange exchange) {
        PaymentEvaluation paymentEvaluation = exchange.getIn().getBody(PaymentEvaluation.class);

        PaymentEvaluationResult.Status evaluationResult = evaluatePayment(paymentEvaluation);

        exchange.getIn().setBody(
            new RawPaymentEvaluationResult(paymentEvaluation, evaluationResult),
                RawPaymentEvaluationResult.class
        );
    }

    private PaymentEvaluationResult.Status evaluatePayment(PaymentEvaluation paymentEvaluation) {
        if (Double.parseDouble(paymentEvaluation.amount()) > 100.0) {
            logger.info("Detected fraudulent payment");

            return PaymentEvaluationResult.Status.REJECTED;
        }

        return PaymentEvaluationResult.Status.ACCEPTED;
    }
}
