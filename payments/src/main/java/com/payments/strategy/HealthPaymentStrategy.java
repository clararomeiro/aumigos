package com.payments.strategy;

import com.payments.model.PaymentResponse;
import org.springframework.stereotype.Component;

@Component("PLANO")
public class HealthPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse process(double valor) {
        return new PaymentResponse(
            "OK",
            "Pagamento pelo plano aprovado. Valor: R$ " + valor
        );
    }
}