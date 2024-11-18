package com.tobsel.api.fraud.config;

import com.tobsel.api.fraud.converter.PaymentEvaluationResultConverter;
import jakarta.annotation.PostConstruct;
import org.apache.camel.CamelContext;
import org.apache.camel.spi.TypeConverterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterRegistry {

    private final CamelContext camelContext;
    private final PaymentEvaluationResultConverter paymentEvaluationResultConverter;

    public ConverterRegistry(CamelContext camelContext, PaymentEvaluationResultConverter paymentEvaluationResultConverter) {
        this.camelContext = camelContext;
        this.paymentEvaluationResultConverter = paymentEvaluationResultConverter;
    }

    @PostConstruct
    public void registerTypeConverters() {
        TypeConverterRegistry registry = camelContext.getTypeConverterRegistry();
        registry.addTypeConverters(paymentEvaluationResultConverter);
    }
}