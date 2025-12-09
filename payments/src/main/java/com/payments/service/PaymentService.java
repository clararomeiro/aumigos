package com.payments.service;

import com.payments.model.PaymentRequest;
import com.payments.model.PaymentResponse;
import com.payments.strategy.PaymentStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {
    private final Map<String, PaymentStrategy> strategies;

    public PaymentService(Map<String, PaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    public PaymentResponse process(PaymentRequest req) {
        String tipo = req.getTipo().toUpperCase();

        PaymentStrategy strategy = strategies.get(tipo);

        if (strategy == null) {
            throw new IllegalArgumentException("Tipo de pagamento não suportado: " + tipo);
        }

        return strategy.process(req.getValor());
    }
}