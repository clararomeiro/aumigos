package com.payments.service;

import com.payments.model.PaymentRequest;
import com.payments.model.PaymentResponse;
import com.payments.strategy.*;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public PaymentResponse process(PaymentRequest req) {

        PaymentStrategy strategy;

        switch (req.getTipo().toUpperCase()) {
            case "C":
                strategy = new CreditPaymentStrategy();
                break;
            case "D":
                strategy = new DebitPaymentStrategy();
                break;
            default:
                strategy = new HealthPaymentStrategy();
                break;
        }

        return strategy.process(req.getValor());
    }
}