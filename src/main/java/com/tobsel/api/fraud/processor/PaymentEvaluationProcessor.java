package com.tobsel.api.fraud.processor;

import com.tobsel.api.fraud.route.model.PaymentEvaluationResult;
import com.tobsel.api.fraud.route.model.PaymentEvaluation;
import com.tobsel.api.fraud.route.model.PaymentStatus;
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

        PaymentEvaluationResult.Status evaluationStatus = evaluatePayment(paymentEvaluation);

        if (evaluationStatus == PaymentEvaluationResult.Status.REJECTED) {
            logger.info("Detected fraudulent payment");
        }

        exchange.getIn().setBody(
                new PaymentEvaluationResult(paymentEvaluation.id(), evaluationStatus),
                PaymentEvaluationResult.class
        );
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }

    private PaymentEvaluationResult.Status evaluatePayment(PaymentEvaluation paymentEvaluation) {
        if (Double.parseDouble(paymentEvaluation.amount()) > 100.0) {
            return PaymentEvaluationResult.Status.REJECTED;
        }

        return PaymentEvaluationResult.Status.ACCEPTED;
    }
}
