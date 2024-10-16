package com.tobsel.api.fraud.processor;

import com.tobsel.api.fraud.repository.PaymentRepository;
import com.tobsel.api.fraud.repository.model.PaymentEntity;
import com.tobsel.api.fraud.route.model.PaymentEvent;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEventProcessor.class);

    private final PaymentRepository paymentRepository;

    public PaymentEventProcessor(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void process(Exchange exchange) {
        logger.info("Saving payment status");
        PaymentEvent paymentConfirmation = exchange.getIn().getBody(PaymentEvent.class);

        paymentRepository.save(
            new PaymentEntity(
                paymentConfirmation.id(),
                paymentConfirmation.userId(),
                paymentConfirmation.amount(),
                paymentConfirmation.timestamp(),
                paymentConfirmation.status().toString()
            )
        );
        logger.info("Finished storing payment status");
    }
}
