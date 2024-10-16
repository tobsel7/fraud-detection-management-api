package com.tobsel.api.fraud.config;

public class Constants {

    public static class RestEndpoint {
        public static final String PAYMENT = "payments";
        public static final String PAYMENT_EVALUATION = PAYMENT + "/evaluations";
    }
    public static class KafkaTopic {
        public static final String PAYMENT = "payment-confirmation";
    }
}
