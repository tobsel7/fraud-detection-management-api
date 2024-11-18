package com.tobsel.api.fraud.converter;

import com.tobsel.api.fraud.route.model.PaymentEvaluation;
import com.tobsel.api.fraud.route.model.PaymentEvaluationResult;
import com.tobsel.api.fraud.route.model.PaymentStatus;
import com.tobsel.api.fraud.route.model.RawPaymentEvaluationResult;
import org.apache.camel.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(generateLoader = true)
public class PaymentEvaluationResultConverter {

    @Converter
    public static PaymentEvaluationResult toPaymentEvaluationResult(
            RawPaymentEvaluationResult rawPaymentEvaluationResult
    ) {
        PaymentEvaluation evaluation = rawPaymentEvaluationResult.paymentEvaluation();
        PaymentEvaluationResult.Status result = rawPaymentEvaluationResult.result();

        return new PaymentEvaluationResult(
                evaluation.id(),
                evaluation.userId(),
                evaluation.amount(),
                evaluation.timestamp(),
                toPaymentStatus(result)
        );
    }

    @Converter
    public static PaymentStatus toPaymentStatus(
            PaymentEvaluationResult.Status paymentEvaluationResultStatus
    ) {
        return paymentEvaluationResultStatus == PaymentEvaluationResult.Status.ACCEPTED
                ? PaymentStatus.PENDING
                : PaymentStatus.REJECTED;
    }
}